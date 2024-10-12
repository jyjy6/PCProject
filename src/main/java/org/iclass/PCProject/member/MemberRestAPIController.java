package org.iclass.PCProject.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")

public class MemberRestAPIController {
    private final MemberRepository memberRepository;


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

}
