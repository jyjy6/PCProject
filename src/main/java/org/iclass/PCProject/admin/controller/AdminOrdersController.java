package org.iclass.PCProject.admin.controller;

import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.admin.service.AdminOrdersService;
import org.iclass.PCProject.statistics.SalesHistory;
import org.iclass.PCProject.statistics.SalesHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminOrdersController {

    private final AdminOrdersService salesHistoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OrdersList")
        public String getOrders(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String price,
                                @RequestParam(required = false) String regdate,
                                Model model) {

        // Pageable 및 Sort 설정
        Pageable pageable = PageRequest.of(page, 10);
        Sort sort = Sort.unsorted();

        // 가격 정렬 설정
        if ("asc".equals(price)) {
            sort = sort.and(Sort.by("price").ascending());
        } else if ("desc".equals(price)) {
            sort = sort.and(Sort.by("price").descending());
        }

        // 등록일 정렬 설정
        if ("asc".equals(regdate)) {
            sort = sort.and(Sort.by("regdate").ascending());
        } else if ("desc".equals(regdate)) {
            sort = sort.and(Sort.by("regdate").descending());
        }

        // 서비스 호출
        Page<SalesHistory> salesHistoryPage = salesHistoryService.getOrders(pageable, sort);

        // 모델에 추가
        model.addAttribute("sales_historys", salesHistoryPage.getContent());
        model.addAttribute("totalPages", salesHistoryPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "kim/adminPage/orders/OrdersList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OrdersWrite")
    public String showOrdersCreateForm() {
        return "kim/adminPage/Orders/OrdersWrite";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/OrdersWrite")
    public String createOrders(@ModelAttribute SalesHistoryDto salesHistoryDto) {
        salesHistoryService.createOrders(salesHistoryDto);
        return "redirect:/adminPage/OrdersList";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OrdersModify")
    public String updateOrdersForm(@RequestParam int seq, Model model) {
        SalesHistory order = salesHistoryService.findById(seq);
        model.addAttribute("sales_history", SalesHistoryDto.toDto(order));
        return "kim/adminPage/orders/OrdersModify";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/OrdersModify")
    public String updateOrders(@ModelAttribute SalesHistory salesHistory,
                               @RequestParam String regdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime parsedDate = LocalDateTime.parse(regdate, formatter);

        salesHistory.setRegdate(parsedDate);
        salesHistoryService.updateOrders(salesHistory);
        return "redirect:/adminPage/OrdersList";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OrdersDelete")
    public String deleteOredersForm(@RequestParam("seq") int seq, Model model) {
        model.addAttribute("orders", salesHistoryService.findById(seq));
        return "kim/adminPage/orders/OrdersDelete";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/OrdersDelete")
    public String removeOrders(@RequestParam("seq") int seq) {
        salesHistoryService.deleteOrders(seq);
        return "redirect:/adminPage/OrdersList";
    }

}

