package org.iclass.PCProject.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")

public class MemberRestAPIController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;


    @PreAuthorize("isAnonymous()")
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

    @PreAuthorize("isAnonymous()")
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

    //이 코드는 아무생각없이 그냥 member테이블의 row하나를 삭제하게끔 돼있는데
    // 이러면 동일아이디 재가입 시 예전주문내역이 출력되고 대 참사가 날 수 있음.
    // 1. 아이디 삭제 직전, 탈퇴유저 테이블을 만들어서 username을 저장한다음 회원가입시 유효성 검사에 추가하거나
    // 2. deletemapping을하지말고 그냥 유저비밀번호, 권한을 바꿔버려서 접근제어를 한다던가 방법을 선택할 수 있음.
    // 확장성 좋은 코드를 본인이 골라서 리팩토링하면 좋을듯
    @PreAuthorize("hasRole('ROLE_USER')")
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
