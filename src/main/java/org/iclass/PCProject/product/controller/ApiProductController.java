package org.iclass.PCProject.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ProductDetailDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.entity.ProductDetail;
import org.iclass.PCProject.product.entity.Review;
import org.iclass.PCProject.product.repository.ProductRepository;
import org.iclass.PCProject.product.service.CartService;
import org.iclass.PCProject.product.service.ProductDetailService;
import org.iclass.PCProject.product.service.ProductService;
import org.iclass.PCProject.product.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ApiProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final ProductDetailService productDetailService;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @GetMapping("/allProducts")
    public ResponseEntity<?> allProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{vendor}")
    public ResponseEntity<?> productsByVendor(@PathVariable String vendor) {
        List<ProductDTO> list = productService.getProductsByVendor(vendor.toUpperCase());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/detail/{seq}")
    public ResponseEntity<?> productsDetailImgsBySeq(@PathVariable Integer seq) {
        List<ProductDetailDTO> list = productDetailService.getProductDetail(seq);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/review/{pSeq}")
    public ResponseEntity<?> reviewList(@PathVariable int pSeq) {
        return ResponseEntity.ok(reviewService.getReviews(pSeq));
    }

    @PostMapping("/cart/updateQuantity")
    public ResponseEntity<?> updateProductQuantity(@RequestParam int pSeq, @RequestParam int qty, @RequestParam String username) {
        Optional<Product> optional = productRepository.findById(pSeq);
        if(optional.isPresent()) {
            Product product = optional.get();
            if(qty > product.getStock()) {
                return ResponseEntity.ok(Map.of("success", false, "message", "재고 수량이 부족합니다. 현재 남은 재고 수량: " + product.getStock()));
            }
            cartService.addItem(pSeq, qty, username);
            return ResponseEntity.ok(Map.of("success", true, "message", "수량이 업데이트 되었습니다."));
        }
        return ResponseEntity.badRequest().build();
    }
}
