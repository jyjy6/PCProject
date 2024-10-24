package org.iclass.PCProject.adminController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.statistics.SalesHistoryService;
import org.iclass.PCProject.statistics.SalesHistory;
import org.iclass.PCProject.statistics.SalesHistoryDto;
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
        LocalDateTime regdate = order.getRegdate();

        String formattedDate = null;
        if (regdate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            formattedDate = regdate.format(formatter);
        }

        model.addAttribute("formattedRegdate", formattedDate);
        model.addAttribute("sales_history", SalesHistoryDto.toDto(order));
        return "kim/adminPage/orders/OrdersModify";
    }

    /*@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/OrdersModify")
    public String updateOrders(@ModelAttribute SalesHistory salesHistory) {
        salesHistoryService.update(salesHistory);
        return "redirect:/adminPage/OrdersList";
    }*/

//    @PostMapping("/OrdersModify")
//    public String updateOrders(@ModelAttribute SalesHistory salesHistory) {
//        // LocalDateTime 변환 로직 추가
//        if (salesHistory.getRegdate() != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//            LocalDateTime regdate = LocalDateTime.parse(salesHistory.getRegdate(), formatter);
//            salesHistory.setRegdate(regdate);
//        }
//
//        salesHistoryService.update(salesHistory);
//        return "redirect:/adminPage/OrdersList";
//    }

}
