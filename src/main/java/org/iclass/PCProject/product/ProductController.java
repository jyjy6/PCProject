package org.iclass.PCProject.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/detail")
    public String detail() {
        return "lee/product_detail";
    }

}
