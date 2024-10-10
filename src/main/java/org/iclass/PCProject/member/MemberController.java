package org.iclass.PCProject.member;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@ModelAttribute Member member) {
        try {
            memberService.registerUser(member);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/sign-up")
    public String signUpPage(Model model){
        model.addAttribute("member", new Member());  // 타임리프에서 사용할 모델 객체
        return "jung/signup.html";
    }



    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response
                           ){
//       @RequestBody로 받은 아이디, 비번
        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );

//        아이디랑 비번 DB내용가 스프링시큐리티가 자동으로 비교해서 로그인해줌
//        (CustomUserDetailsService의 loadUserByUsername함수 있어야함)
        System.out.println("오스토큰:"+authToken);
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        System.out.println("오스:"+auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var latestAuth = SecurityContextHolder.getContext().getAuthentication();
        var jwt = JwtUtil.createToken(latestAuth);
        System.out.println(jwt);

        var cookie = new Cookie("jwt",jwt);
        cookie.setMaxAge(20);
        //쿠키 자바스크립트로 조작하기 어려워짐
        cookie.setHttpOnly(true);
        //쿠키가 전송될 URL
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    String mypageJWT(HttpServletRequest request){


        return "sdf";
    }


}
