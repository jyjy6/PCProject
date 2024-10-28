package org.iclass.PCProject.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.member.MemberService;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ReviewDTO;
import org.iclass.PCProject.product.service.ProductService;
import org.iclass.PCProject.product.service.ReviewService;
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

    @PostMapping("/comment/add")
    public ResponseEntity<?> addReview(@RequestBody Map<String, Object> map, @RequestParam("pSeq") int seq, Authentication auth) {

        if(auth != null) {
            String username = memberService.memberInfo(auth).getUsername();
            ProductDTO dto = productService.getProductBySeq(seq);
            reviewService.addReview(map, dto, username);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/comment/list")
    public ResponseEntity<?> commentList() {

        return null;
    }

    @DeleteMapping("/commenet/remove")
    public ResponseEntity<?> removeReview() {

        return null;
    }
}
