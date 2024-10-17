package org.iclass.PCProject.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ApiProductController {

    private final ProductService productService;

    @GetMapping("/allProducts")
    public ResponseEntity<?> allProducts() {
        return ResponseEntity.ok(productService.getAllProductsList());
    }

    @GetMapping("/{vendor}")
    public ResponseEntity<?> samsungProducts(@PathVariable String vendor) {
        List<ProductDTO> list = productService.getProductsByVendor(vendor);
        return ResponseEntity.ok(list);
    }
}
