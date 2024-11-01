package org.iclass.PCProject.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.repository.CartRepository;
import org.iclass.PCProject.qna.QNARepository;
import org.iclass.PCProject.security.CustomUserDetails;
import org.iclass.PCProject.statistics.SalesHistory;
import org.iclass.PCProject.statistics.SalesHistoryRepository;
import org.iclass.PCProject.statistics.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final QNARepository qnaRepository;
    private final CartRepository cartRepository;
    private final SalesHistoryRepository salesHistoryRepository;
    private final StatisticsService statisticsService;


    @PreAuthorize("isAnonymous()")
    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@RequestBody Member member) {
        try {
            memberService.registerUser(member);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping("/sign-up")
    public String signUpPage() {
        return "jung/signup";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login(){
        return "jung/login.html";
    }


    @GetMapping("/mypage/{id}")
    public String myPage(@PathVariable("id") String id,
                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                         Model model,
                         Authentication auth,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // 이 코드는 PathVariable을 통해 데이터 바인딩해서
//         <div th:replace="~{jung/mypage/__${id}__ :: content}"></div> 이 값을 유동적으로 바꿈
        model.addAttribute("id", id);

        //유저 id를 auth정보에서 추출
        String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();

        if(auth!=null) {
            //상담 진행중인 개수가 몇개인지 바인딩.
            var qnaLength = qnaRepository.findByQuestionerAndAnswerIsNull(username).size();
            model.addAttribute("qnaLength", qnaLength);
            //모든 상담의 개수 바인딩
            var qnaAllLength = qnaRepository.findByQuestioner(username).size();
            model.addAttribute("qnaAllLength", qnaAllLength);
            //장바구니에 담긴 개수 바인딩
            var cartLength = cartRepository.findAllByUsernameOrderByRegDateDesc(username).size();
            model.addAttribute("cartLength", cartLength);
            //주문내역 건수를 바인딩
            var purchaseLength = salesHistoryRepository.findByUsername(username);
            model.addAttribute("purchaseLength", purchaseLength.size());
        }

        //qna페이지로 이동 시 해당 유저의 질문들을 바인딩(3개만)
        if (id.equals("qna") && auth != null) {
            var qnaList = qnaRepository.findTop3ByQuestionerOrderByRegDateDesc(username);
            model.addAttribute("qnaList", qnaList);
        }

        if (id.equals("orders") || id.equals("main") && auth != null) {
            // 기본값 설정 (전체 조회용)
            List<SalesHistory> purchaseList;

            if (startDate == null) {
                startDate = LocalDate.of(2000, 1, 1);
            }
            if (endDate == null) {
                endDate = LocalDate.now(); // 오늘 날짜로 설정
            }

            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

            purchaseList = statisticsService.findPurchaseHistory(
                    username, startDateTime, endDateTime);
            model.addAttribute("purchaseList", purchaseList);

            // 각 상태별 주문 개수 초기화
            int[] statusCounts = new int[4]; // 0: 주문완료, 1: 결제완료, 2: 배송중, 3: 배송완료

            // 각 상태별 개수 세기
            for (SalesHistory purchase : purchaseList) {
                int status = purchase.getStslogis(); // stslogis 값 가져오기
                if (status >= 0 && status < statusCounts.length) {
                    statusCounts[status] += 1; // 해당 상태 카운트 증가
                }
            }
            // 모델에 상태별 카운트 추가
            model.addAttribute("orderCompletedCount", statusCounts[0]);
            model.addAttribute("paymentCompletedCount", statusCounts[1]);
            model.addAttribute("shippingCount", statusCounts[2]);
            model.addAttribute("deliveredCount", statusCounts[3]);
        }

        return "jung/mypage/mypage"; // 주 템플릿 경로
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/member/modify")
    public String modify(Authentication auth,
                         HttpSession session,  // HttpSession을 매개변수로 추가
                         Model model) {
        // 세션에서 validated 값 가져오기
        Boolean validated = (Boolean) session.getAttribute("validated");
        auth.getAuthorities();
        var userRole = auth.getAuthorities().toString(); // 권한 목록을 문자열로 변환
        System.out.println(userRole.contains("ROLE_OAuth"));
        if (userRole.contains("ROLE_OAuth")) { // "ROLE_OAuth" 포함 여부 확인
            MemberDTO member = memberService.memberInfo(auth);
            model.addAttribute("member", member);
            return "jung/modify.html";
        }

        // 검증이 되지 않은 경우 리다이렉트
        if (validated == null || !validated) {
            return "redirect:/member/validate"; // 검증 페이지로 리다이렉트
        }
        // 검증이 된 경우 회원 정보 가져오기
        MemberDTO member = memberService.memberInfo(auth);
        model.addAttribute("member", member);
        return "jung/modify.html";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/member/validate")
    public String validate(Authentication auth,
                           Model model){
        // ROLE_OAuth 인 경우 바로 수정페이지로 리다이렉트
        boolean hasOAuthRole = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OAuth"));

        if (hasOAuthRole) {
            return "redirect:/member/modify"; // ROLE_OAuth인 경우 리다이렉트
        }
        //아닌경우엔 검증페이지로 이동
        var username = ((CustomUserDetails)auth.getPrincipal()).getUsername();
        model.addAttribute("username", username);
        return "jung/memberValidatePage";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/member/withdraw")
    public String withdraw(Authentication auth,
                           Model model){
        var username = ((CustomUserDetails)auth.getPrincipal()).getUsername();
        model.addAttribute("username", username);
        return "jung/memberWithdrawPage";
    }

//    @GetMapping("/mypage/orders")
//    public String buyHistory(@PathVariable(required = false) String condition,
//                             @RequestParam(value = "page", defaultValue = "1") int page,
//                             @RequestParam(defaultValue = "3") int size,
//                             @RequestParam(value = "search", required = false) String search, // 검색어 추가
//                             Model model,
//                             Authentication auth){
//        //유저 id를 auth정보에서 추출
//        String username = ((CustomUserDetails) auth.getPrincipal()).getUsername();
//        var purchaseList = salesHistoryRepository.findByUsername(username);
//
//        model.addAttribute("purchaseList", purchaseList);
//        System.out.println(purchaseList.toString());
//
//        return "jung/mypage/buyHistory";
//    }










}