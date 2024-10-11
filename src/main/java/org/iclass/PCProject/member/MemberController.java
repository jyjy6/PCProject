package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
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
    @GetMapping("/sign-up")
    public String signUpPage() {
        return "jung/signup";
    }
    @GetMapping("/api/member/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = memberRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/api/member/check-displayName")
    public ResponseEntity<Boolean> checkDisplayName(@RequestParam("displayName") String displayName) {
        boolean exists = memberRepository.existsByDisplayName(displayName);
        return ResponseEntity.ok(exists);
    }


    @GetMapping("/login")
    public String login(){
        return "jung/login.html";
    }






}