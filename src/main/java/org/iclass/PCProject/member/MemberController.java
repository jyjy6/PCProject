package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@RequestBody Member member) {
        try {
            memberService.registerUser(member);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "jung/signup";
    }

    @GetMapping("/login")
    public String login(){
        return "jung/login.html";
    }

    @GetMapping("/mypage/{id}")
    public String myPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "jung/mypage/mypage"; // 주 템플릿 경로
    }




}