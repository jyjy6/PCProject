package org.iclass.PCProject.product.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.member.MemberService;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ReviewDTO;
import org.iclass.PCProject.product.entity.Review;
import org.iclass.PCProject.product.repository.ReviewRepository;
import org.iclass.PCProject.product.service.ProductService;
import org.iclass.PCProject.product.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final MemberService memberService;
    private final ReviewRepository reviewRepository;

    @PostMapping("/comment/add")
    public ResponseEntity<?> addReview(@RequestBody Map<String, Object> map, @RequestParam("pSeq") int seq, Authentication auth) {
        if (auth != null) {
            String username = memberService.memberInfo(auth).getUsername();
            ProductDTO dto = productService.getProductBySeq(seq);
            reviewService.addReview(map, dto, username);
            System.out.println(map);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/comment/list/{productSeq}")
    public ResponseEntity<?> commentList(@PathVariable int productSeq) {
        try {
            // productSeq에 해당하는 리뷰 목록을 조회
            List<ReviewDTO> comments = reviewService.getReviews(productSeq);

            // 조회된 데이터가 있을 때의 응답
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            // 예외 발생 시 서버 오류 응답
            return ResponseEntity.status(500).body("Failed to retrieve comments");
        }
    }

    @Transactional
    @DeleteMapping("/comment/remove")
    public ResponseEntity<?> removeReview(@RequestParam("seq") int seq) {
        try {
            reviewRepository.deleteBySeq(seq);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제에 실패했습니다.");
        }
    }

}
