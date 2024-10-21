package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.qna.QNA;
import org.iclass.PCProject.qna.QNARepository;
import org.iclass.PCProject.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final QNARepository qnaRepository;




    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@RequestBody Member member) {
        try {
            memberService.registerUser(member);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

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
                         Authentication auth) {
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
        }
        //qna페이지로 이동 시 해당 유저의 질문들을 바인딩(3개만)
        if (id.equals("qna") && auth != null) {
            var qnaList = qnaRepository.findTop3ByQuestionerOrderByRegDateDesc(username);
            model.addAttribute("qnaList", qnaList);
        }


        return "jung/mypage/mypage"; // 주 템플릿 경로
    }

    @GetMapping("/member/modify")
    public String modify(Authentication auth,
                         Model model){
        MemberDTO member = memberService.memberInfo(auth);
        model.addAttribute("member",member);
        return "jung/modify.html";
    }


    @GetMapping("/member/validate")
    public String validate(Authentication auth,
                           Model model){
        var username = ((CustomUserDetails)auth.getPrincipal()).getUsername();
        model.addAttribute("username", username);
        return "jung/memberValidatePage";
    }

    @GetMapping("/member/withdraw")
    public String withdraw(Authentication auth,
                           Model model){
        var username = ((CustomUserDetails)auth.getPrincipal()).getUsername();
        model.addAttribute("username", username);
        return "jung/memberWithdrawPage";
    }




}