package org.iclass.PCProject.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")

public class MemberRestAPIController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@RequestBody Member member) {
        try {
            memberService.registerUser(member);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = memberRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-displayName")
    public ResponseEntity<Boolean> checkDisplayName(@RequestParam("displayName") String displayName) {
        boolean exists = memberRepository.existsByDisplayName(displayName);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestBody Member member,
                                             Authentication auth) {
        try {
            memberService.modifyUser(member, auth);
            return new ResponseEntity<>("User modify successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> requestBody,
                                             Authentication auth) {
        String username = requestBody.get("username"); // JSON에서 username 추출

        try {
            memberService.deleteMember(username, auth);
            return new ResponseEntity<>("User delete successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/validate")
    public ResponseEntity<String> validatePassword(@RequestBody Map<String, String> requestBody,
                                                   HttpSession session) {
        ResponseEntity<String> response = memberService.validatePw(requestBody);

        // 검증이 성공한 경우 세션에 validated 속성 추가
        if (response.getStatusCode() == HttpStatus.OK) {
            session.setAttribute("validated", true); // 검증 성공 시 세션에 validated=true 추가
        }

        return response;
    }


}
