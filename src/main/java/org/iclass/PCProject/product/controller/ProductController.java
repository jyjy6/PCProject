package org.iclass.PCProject.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.member.MemberService;
import org.iclass.PCProject.product.dto.CartDTO;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.dto.ProductPaymentDTO;
import org.iclass.PCProject.product.service.CartService;
import org.iclass.PCProject.product.service.ProductDetailService;
import org.iclass.PCProject.product.service.ProductPaymentService;
import org.iclass.PCProject.product.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;
    private final ProductPaymentService paymentService;

    @GetMapping(value = {"/", "/삼성", "/lg", "/hp", "/asus", "/acer"})
    public String home(Model model, HttpServletRequest request) {

        if (request.getServletPath().equals("/")) {
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
//        model.addAttribute("detailImgs", detailService.getProductDetail(seq));
        model.addAttribute("avgScore", productService.getAvgScore(seq));

        if (auth != null) {
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
    public String addCart(
            @RequestParam("pSeq") Integer pSeq,
            @RequestParam("qty") int qty,
            @RequestParam(value = "purchaseDirect", required = false) String purchaseDirect,
            RedirectAttributes redirectAttributes,
            Authentication auth) {

        if (auth != null) {
            String username = memberService.memberInfo(auth).getUsername();
            cartService.addItem(pSeq, qty, username);

        } else {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요한 서비스입니다.");
            return "redirect:/product_detail/" + pSeq;
        }

        return "redirect:/cart";
    }


    @GetMapping("/purchase_products")
    public String purchaseProducts(Authentication auth, Model model) {

        if (auth == null) {
            model.addAttribute("allProducts", productService.getAllProducts());
            model.addAttribute("recommendedProducts", productService.recommendedProducts());
            return "home";
        }

        String username = memberService.memberInfo(auth).getUsername();
        List<ProductPaymentDTO> dtos = paymentService.getAllItemsByUsername(username);
        List<ProductDTO> products = productService.getAllProducts();

        for(ProductPaymentDTO dto : dtos) {
            for(ProductDTO product : products) {
                if(dto.getPSeq() == product.getSeq() && dto.getQuantity() > product.getStock()) {
                    dto.setQuantity(product.getStock());
                } else if(dto.getPSeq() == product.getSeq() && product.getStock() <= 0) {
                    dto.setQuantity(0);
                }
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("items", dtos);
        model.addAttribute("products", products);

        return "lee/product/purchase_products";
    }

    @PostMapping("/purchase_products")
    public String purchaseProducts(Authentication auth, RedirectAttributes redirectAttributes,
                                   @RequestParam("pSeq") List<Integer> pSeq,
                                   @RequestParam(value = "qty", required = false) Integer qty) {

        if (auth != null) {
            String username = memberService.memberInfo(auth).getUsername();
            paymentService.addItems(username, pSeq);
            cartService.removeItems(pSeq, username);
        } else {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요한 서비스입니다.");
            return "redirect:/";
        }

        if(pSeq.size() == 1 && qty != null ){
            String username = memberService.memberInfo(auth).getUsername();
            cartService.addItem(pSeq.get(0), qty, username);
        }

        return "redirect:/purchase_products";
    }

    @PostMapping("/payment")
    @Transactional
    public String doPayment(Authentication auth, @RequestParam("itemPseqs") Integer[] pSeqs, RedirectAttributes redirectAttributes) {

        if (auth != null) {
            String username = memberService.memberInfo(auth).getUsername();
            for (int pSeq : pSeqs) {
                paymentService.saveBypSeqIntoSalesHistory(pSeq);
                paymentService.updateStock(pSeq);
                paymentService.saveAllBypSeq(pSeq);
                paymentService.deleteItemsDonePurchasing(pSeq, username);
            }

            redirectAttributes.addFlashAttribute("message", "결제가 완료되었습니다.");
            return "redirect:/mypage/orders";
        }
        redirectAttributes.addFlashAttribute("message", "결제가 실패하였습니다. 다시 시도해 주세요.");
        return "redirect:/purchase_products";
    }
}