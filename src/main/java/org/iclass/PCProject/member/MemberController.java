package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.security.CustomUserDetails;
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



    @GetMapping("/")
    public String index(){
        return "home";
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


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/{id}")
    public String myPage(@PathVariable("id") String id, Model model) {
//        PathVariable을 통해 데이터 바인딩해서
//        <div th:replace="~{jung/mypage/__${id}__ :: content}"></div> 이 값을 유동적으로 바꿈
        model.addAttribute("id", id);

        // 더미 데이터 생성
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("아이템 1", 1000, 1, "M", "빨강", "https://via.placeholder.com/250"));
        cartItems.add(new CartItem("아이템 2", 2000, 2, "L", "파랑", "https://via.placeholder.com/250"));
        cartItems.add(new CartItem("아이템 3", 3000, 1, "S", "초록", "https://via.placeholder.com/250"));

        model.addAttribute("cartItems", cartItems);
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