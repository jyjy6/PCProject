package org.iclass.PCProject.adminController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
import org.iclass.PCProject.product.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductList")
    public String getProducts(@RequestParam(required = false) String vendor,
                              @RequestParam(required = false) String regDate,
                              @RequestParam(required = false) String price,
                              @RequestParam(required = false) String stock,
                              Model model) {
        List<ProductDTO> products = productService.getAllProducts();

        if (vendor != null) {
            products = products.stream()
                    .filter(product -> product.getVendor().equals(vendor))
                    .collect(Collectors.toList());
        }

        if ("asc".equals(regDate)) {
            products.sort(Comparator.comparing(ProductDTO::getRegDate, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("desc".equals(regDate)) {
            products.sort(Comparator.comparing(ProductDTO::getRegDate, Comparator.nullsLast(Comparator.reverseOrder())));
        }

        if ("asc".equals(price)) {
            products.sort(Comparator.comparing(ProductDTO::getPrice, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("desc".equals(price)) {
            products.sort(Comparator.comparing(ProductDTO::getPrice, Comparator.nullsLast(Comparator.reverseOrder())));
        }

        if ("asc".equals(stock)) {
            products.sort(Comparator.comparing(ProductDTO::getStock, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("desc".equals(stock)) {
            products.sort(Comparator.comparing(ProductDTO::getStock, Comparator.nullsLast(Comparator.reverseOrder())));
        }
        model.addAttribute("products", products);
        return "kim/adminPage/product/ProductList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductWrite")
    public String showProductCreateForm() {
        return "kim/adminPage/product/ProductWrite";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ProductWrite")
    public String createProduct(@ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return "redirect:/adminPage/ProductList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductModify")
    public String updateProductForm(@RequestParam("seq") int seq, Model model) {
        var product = productService.getProductBySeq(seq);
        model.addAttribute("product", product);
        return "kim/adminPage/product/ProductModify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ProductModify")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return "redirect:/adminPage/ProductList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductDelete")
    public String deleteProductForm(@RequestParam("seq") int seq, Model model) {
        model.addAttribute("product", productService.getProductBySeq(seq));
        return "kim/adminPage/product/ProductDelete";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ProductDelete")
    public String removeProduct(@RequestParam("seq") Product seq) {
        productService.deleteProduct(seq);
        return "redirect:/adminPage/ProductList";
    }
}
