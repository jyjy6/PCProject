package org.iclass.PCProject.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.member.MemberService;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.service.CartService;
import org.iclass.PCProject.product.service.ProductDetailService;
import org.iclass.PCProject.product.service.ProductService;
import org.iclass.PCProject.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductDetailService detailService;
    private final MemberService memberService;
    private final CartService cartService;

    @GetMapping(value = {"/", "/삼성", "/lg", "/hp", "/asus", "/acer"})
    public String home(Model model, HttpServletRequest request) {

        if(request.getServletPath().equals("/")) {
            model.addAttribute("allProducts", productService.getAllProducts());
        } else {
            model.addAttribute("allProducts", productService.getProductsByVendor(request.getServletPath().substring(1).toUpperCase()));
        }
        model.addAttribute("recommendedProducts", productService.recommendedProducts());

        return "home";
    }

    @GetMapping("/product_detail/{seq}")
    public String detailProd(@PathVariable("seq") int seq, Model model, Authentication auth) {

        model.addAttribute("product", productService.getProductBySeq(seq));
        model.addAttribute("detailImgs", detailService.getProductDetail(seq));

        if(auth != null) {
            model.addAttribute("username", memberService.memberInfo(auth).getUsername());
        }

        return "lee/product/product_detail";
    }

    @GetMapping("/cart")
    public String addCart(Authentication auth, Model model) {

        if (auth == null) {
            model.addAttribute("allProducts", productService.getAllProducts());
            model.addAttribute("recommendedProducts", productService.recommendedProducts());
            return "home";
        }

        String username = memberService.memberInfo(auth).getUsername();
        List<CartDTO> items = cartService.getItems(username);
        List<ProductDTO> products = productService.getAllProducts();

        if (items == null || items.isEmpty()) items = new ArrayList<>();

        model.addAttribute("username", username);
        model.addAttribute("products", products);
        model.addAttribute("items", items);

        return "lee/product/cart";
    }


    @PostMapping("/cart")
    public String addCart(@RequestParam("pSeq") Integer pSeq, @RequestParam("qty") int qty, RedirectAttributes redirectAttributes, Authentication auth) {

        if(auth != null) {
            String username = memberService.memberInfo(auth).getUsername();
            cartService.addItem(pSeq, qty, username);

        } else {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요한 서비스입니다.");
            return "redirect:/product_detail/" + pSeq;
        }

        return "redirect:/cart";
    }

}
