package org.iclass.PCProject.product.controller;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDTO> list = productService.list();
        model.addAttribute("productsList", list);
        return "home";
    }

    @GetMapping("/detail")
    public String detail() {
        return "lee/product_detail";
    }

}
