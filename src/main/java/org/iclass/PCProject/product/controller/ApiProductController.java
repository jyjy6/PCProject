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

    @PostMapping("/cart/updateQty")
    public ResponseEntity<?> updateProductQuantity(@RequestBody Map<String, Object> req) {
        int pSeq = (int) req.get("pSeq");
        int qty = (int) req.get("qty");
        String username = req.get("username").toString();

        cartService.updateQuantity(pSeq, qty, username);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/deleteItems")
    public ResponseEntity<?> deleteCartItems(@RequestBody Map<String, Object> req) {
        List<Integer> pSeqs = (List<Integer>) req.get("pSeqs");
        String username = req.get("username").toString();

        log.info(":::req: {}:::" ,req);
        log.info(":::req.get('pSeqs'): {}:::", req.get("pSeqs"));
        log.info(":::pSeqs: {}:::", pSeqs.get(0));
        cartService.removeItems(pSeqs, username);

        return ResponseEntity.ok().build();
    }
}
