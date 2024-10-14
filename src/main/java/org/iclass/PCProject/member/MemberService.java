package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member registerUser(Member member) {
        // 사용자 이름, 닉네임이 이미 존재하는지 확인
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new RuntimeException("Username already exists");
        } else if (memberRepository.existsByDisplayName(member.getDisplayName())) {
            throw new RuntimeException("Display name already exists");
        }

        // 비밀번호가 비어 있지 않으면 암호화
        if (member.getPassword() == null || member.getPassword().isEmpty()) {
            throw new RuntimeException("Password cannot be empty"); // 비밀번호가 없으면 예외 처리
        }
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // 사용자 저장
        return memberRepository.save(member);
    }


    public Member modifyUser(Member member, Authentication auth) {
        String username = auth.getName();
        if(!username.equals(member.getUsername())){
            throw new IllegalArgumentException("아이디는 수정할 수 없습니다.");
        }
        Member editTargetMember = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Member not found"));
        member.setId(editTargetMember.getId());
        member.setRole(editTargetMember.getRole());

        // 비밀번호 처리 (이전 설명에서 했던 방법을 적용)
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
        } else {
            member.setPassword(editTargetMember.getPassword());
        }
        // 사용자 저장
        return memberRepository.save(member);
    }

    public Member memberInfo(Authentication auth){
        var userId = ((CustomUserDetails)auth.getPrincipal()).getId();
        Member member = memberRepository.findById(userId).orElseThrow(()->new RuntimeException("그런 아이디 없음"));
        return member;
    }

    public ResponseEntity<String> validatePw(@RequestBody Map<String, String> requestBody){
        String id = requestBody.get("id"); // id 추출
        String pw = requestBody.get("pw"); // pw 추출

        var targetMember = memberRepository.findByUsername(id)
                .orElseThrow(() -> new RuntimeException("Value not found!"));

        var correctPassword = targetMember.getPassword();

        // 비밀번호 검증
        if (passwordEncoder.matches(pw, correctPassword)) {
            return ResponseEntity.ok().body("Valid");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid password");
        }

    }



}
