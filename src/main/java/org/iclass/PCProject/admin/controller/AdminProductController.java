package org.iclass.PCProject.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.admin.service.AdminProductService;
import org.iclass.PCProject.product.dto.ProductDTO;
import org.iclass.PCProject.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    private Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer value: " + value);
            return null;
        }
    }

    private Integer parseStock(String stock) {
        if (stock == null || stock.isEmpty()) {
            return null;
        }

        try {
            return Integer.valueOf(stock);
        } catch (NumberFormatException e) {
            System.err.println("Invalid stock value: " + stock);
            return null;
        }
    }

    private LocalDateTime parseRegDate(String regDate) {
        if (regDate == null || regDate.isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(regDate);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + regDate);
            return null;
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductList")
    public String getProducts(@RequestParam(required = false) String vendor,
                              @RequestParam(required = false) String regDate,
                              @RequestParam(required = false) String price,
                              @RequestParam(required = false) String stock,
                              @RequestParam(defaultValue = "0") int page,
                              HttpServletRequest request,
                              Model model) {

        Pageable pageable = PageRequest.of(page, 10);

        Sort sort = Sort.unsorted();

        if ("asc".equals(request.getParameter("regDateSort"))) {
            sort = sort.and(Sort.by("regDate").ascending());
        } else if ("desc".equals(request.getParameter("regDateSort"))) {
            sort = sort.and(Sort.by("regDate").descending());
        }

        if ("asc".equals(request.getParameter("priceSort"))) {
            sort = sort.and(Sort.by("price").ascending());
        } else if ("desc".equals(request.getParameter("priceSort"))) {
            sort = sort.and(Sort.by("price").descending());
        }

        if ("asc".equals(request.getParameter("stockSort"))) {
            sort = sort.and(Sort.by("stock").ascending());
        } else if ("desc".equals(request.getParameter("stockSort"))) {
            sort = sort.and(Sort.by("stock").descending());
        }

        pageable = PageRequest.of(page, 10, sort);

        LocalDateTime parsedRegDate = parseRegDate(regDate);
        Integer priceValue = parseInteger(price);
        Integer stockValue = parseStock(stock);

        Page<ProductDTO> productsPage = adminProductService.getProducts(vendor, parsedRegDate, priceValue, stockValue, pageable);

        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("vendor", vendor);
        model.addAttribute("regDate", regDate);
        model.addAttribute("price", price);
        model.addAttribute("stock", stock);
        model.addAttribute("regDateSort", request.getParameter("regDateSort"));
        model.addAttribute("priceSort", request.getParameter("priceSort"));
        model.addAttribute("stockSort", request.getParameter("stockSort"));

        return "kim/adminPage/product/ProductList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductSearch")
    public String searchProducts(@RequestParam String search, Model model) {
        List<Product> products = adminProductService.findByFilters(search);
        model.addAttribute("products", products);
        model.addAttribute("search", search);
        return "kim/adminPage/product/ProductSearch";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductWrite")
    public String showProductCreateForm() {
        return "kim/adminPage/product/ProductWrite";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ProductWrite")
    public String createProduct(@ModelAttribute ProductDTO productDTO) {
        adminProductService.createProduct(productDTO);
        return "redirect:/adminPage/ProductList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductModify")
    public String updateProductForm(@RequestParam("seq") int seq, Model model) {
        var product = adminProductService.getProductBySeq(seq);
        model.addAttribute("product", product);
        return "kim/adminPage/product/ProductModify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ProductModify")
    public String updateProduct(@ModelAttribute("product") ProductDTO productDTO) {
        adminProductService.updateProduct(productDTO);
        return "redirect:/adminPage/ProductList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ProductDelete")
    public String deleteProductForm(@RequestParam("seq") int seq, Model model) {
        model.addAttribute("product", adminProductService.getProductBySeq(seq));
        return "kim/adminPage/product/ProductDelete";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/ProductDelete")
    public String removeProduct(@RequestParam("seq") int seq) {
        adminProductService.deleteProduct(seq);
        return "redirect:/adminPage/ProductList";
    }
}