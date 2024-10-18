package org.iclass.PCProject.qna;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/qna")
@RestController
public class QNARestAPIController {
    private final QNAService qnaService;

    @PostMapping("/add")
    public ResponseEntity<String> addQNA(@RequestBody QNA qna) {
        try {
            qnaService.save(qna);
            return ResponseEntity.status(HttpStatus.CREATED).body("문의가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            // 에러 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의 등록 중 오류가 발생했습니다.");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/answer")
    public ResponseEntity<?> updateAnswer(@RequestBody QNA answer) {
        try {
            qnaService.modify(answer);
            return ResponseEntity.status(HttpStatus.CREATED).body("답변이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            // 에러 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 등록 중 오류가 발생했습니다.");
        }
    }

}
