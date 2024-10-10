package org.iclass.PCProject.member;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if(cookies.length == 0){
            //쿠키가 0이면 다음필터 실행
            filterChain.doFilter(request,response);
            return;
        }
        var jwtCookie = "";
        for (int i = 0; i < cookies.length; i++){
            if (cookies[i].getName().equals("jwt")){
                jwtCookie = cookies[i].getValue();
                break;
            }
        }
        System.out.println(jwtCookie);

        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e){
            filterChain.doFilter(request,response);
            return;
        }

        // JWT에서 필요한 정보 추출
        String username = claim.get("username").toString();
        String displayName = claim.get("displayName").toString();
        String authoritiesString = claim.get("authorities") != null ? claim.get("authorities").toString() : "";
        List<GrantedAuthority> authorities = Arrays.stream(authoritiesString.split(","))
                .filter(auth -> !auth.isEmpty()) // 빈 문자열을 필터링
                .map(SimpleGrantedAuthority::new) // 각 권한 문자열을 GrantedAuthority로 변환
                .collect(Collectors.toList());


        // 콘솔에 정보 출력
        System.out.println("Username: " + username);
        System.out.println("DisplayName: " + displayName);
        System.out.println("Authorities " + authorities);

        // DB 조회 없이 JWT의 정보로 인증 처리
        // CustomUserDetails 생성
        CustomUserDetails customUser = new CustomUserDetails(new Member(username, displayName, authorities));// JWT의 Claims로 Member 객체 생성

        var authToken = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
    }

}


//1. jwt이름의 쿠키가 있으면 꺼내서
//2. 유효기간, 위조여부 등 확인해보고
//3. 문제 없으면 auth 변수에 유저정보 넣어줌.