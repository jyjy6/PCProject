package org.iclass.PCProject.member.service;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.member.entity.Member;
import org.iclass.PCProject.member.dto.MemberDTO;
import org.iclass.PCProject.member.repsitory.MemberRepository;
import org.iclass.PCProject.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<MemberDTO> getAllMemberList() {
        List<Member> list = memberRepository.findAll();
        return list.stream().map(MemberDTO::toDto).collect(Collectors.toList());
    }

    public Member findById(Long seq) {
        Optional<Member> memberOpt = memberRepository.findById(seq);
        return memberOpt.orElse(null); // 존재하지 않으면 null 반환
    }

    public void update(Member member) {
        memberRepository.save(member);
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

    public MemberDTO memberInfo(Authentication auth){
        var userId = ((CustomUserDetails)auth.getPrincipal()).getId();
        Member member = memberRepository.findById(userId).orElseThrow(()->new RuntimeException("그런 아이디 없음"));

        MemberDTO userInfo = MemberDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .displayName(member.getDisplayName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .role(member.getRole())
                .gender(member.getGender())
                .age(member.getAge())
                .address(member.getAddress())
                .address2(member.getAddress2())
                .build();

        return userInfo;
    }

    public ResponseEntity<String> validatePw(@RequestBody Map<String, String> requestBody){
        String id = requestBody.get("username"); // id 추출
        String pw = requestBody.get("password"); // pw 추출

        var targetMember = memberRepository.findByUsername(id)
                .orElseThrow(() -> new NoSuchElementException("Value not found!"));

        var correctPassword = targetMember.getPassword();

        // 비밀번호 검증
        if (passwordEncoder.matches(pw, correctPassword)) {
            return ResponseEntity.ok().body("Valid");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid password");
        }

    }

    public ResponseEntity<String> deleteMember(String username, Authentication auth) {
        // 인증된 사용자 이름 가져오기
        String authUsername = ((CustomUserDetails)auth.getPrincipal()).getUsername();
        // 인증된 사용자 이름으로 회원 조회
        Member member = memberRepository.findByUsername(authUsername)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디가 존재하지 않습니다."));
        // 요청된 사용자 이름과 조회된 사용자 이름이 다르면 예외 반환
        if (!username.equals(member.getUsername())) {
            throw new IllegalArgumentException("아이디가 다릅니다.");
        }
        // 회원 삭제
        memberRepository.delete(member);
        // 성공적으로 삭제되었음을 알리는 응답 반환
        return new ResponseEntity<>("회원이 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }



}
