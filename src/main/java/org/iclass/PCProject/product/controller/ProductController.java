package org.iclass.PCProject.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.service.ProductDetailService;
import org.iclass.PCProject.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductDetailService detailService;

    @GetMapping(value = {"/", "/삼성", "/lg", "/hp", "/asus", "/acer"})
    public String home(Model model, HttpServletRequest request) {
        if(request.getServletPath().equals("/")) {
            model.addAttribute("allProducts", productService.getAllProductsList());
        } else {
            model.addAttribute("allProducts", productService.getProductsByVendor(request.getServletPath().substring(1).toUpperCase()));
        }
        model.addAttribute("recommendedProducts", productService.recommendedProducts());
        return "home";
    }

    @GetMapping("/product_detail/{seq}")
    public String detail(@PathVariable("seq") int seq, Model model) {
//        productService.getRecentThumbnailBySeq(seq);
        model.addAttribute("product", productService.getProductBySeq(seq));
        model.addAttribute("detailImgs", detailService.getProductDetailImgs(seq));
        return "lee/product_detail";
    }
}
