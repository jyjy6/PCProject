package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        // 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // 사용자 저장
        return memberRepository.save(member);
    }
}
