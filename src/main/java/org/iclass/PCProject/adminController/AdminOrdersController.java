package org.iclass.PCProject.adminController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.salesHis.SalesHistoryDto;
import org.iclass.PCProject.salesHis.SalesHistory;
import org.iclass.PCProject.salesHis.SalesHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminOrdersController {

    private final SalesHistoryService salesHistoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OrdersList")
    public String ordersList(Model model, @RequestParam(required = false) String price) {
        List<SalesHistoryDto> orders = salesHistoryService.getAllSalesHistoryList();

        if("asc".equals(price)) {
            orders.sort(Comparator.comparing(SalesHistoryDto::getPrice, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if("desc".equals(price)) {
            orders.sort(Comparator.comparing(SalesHistoryDto::getPrice, Comparator.nullsLast(Comparator.reverseOrder())));
        }

        model.addAttribute("sales_historys", orders);
        return "kim/adminPage/orders/OrdersList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/OrdersModify")
    public String updateOrdersForm(@RequestParam int seq, Model model) {
        SalesHistory order = salesHistoryService.findById(seq);
        model.addAttribute("sales_history", SalesHistoryDto.toDto(order));
        return "kim/adminPage/orders/OrdersModify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/adminPage/OrdersModify")
    public String updateOrders(@ModelAttribute SalesHistory salesHistory) {
        salesHistoryService.update(salesHistory);
        return "redirect:/adminPage/OrdersList";
    }
}
