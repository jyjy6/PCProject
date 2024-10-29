package org.iclass.PCProject.admin.controller;

import lombok.RequiredArgsConstructor;
import org.iclass.PCProject.admin.service.AdminCustomerService;
import org.iclass.PCProject.member.MemberDTO;
import org.iclass.PCProject.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/CustomerList")
    public String customerList(@RequestParam(required = false) String year,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false) String sortDirection,
                               Model model) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<MemberDTO> membersPage = adminCustomerService.findByCreatedAtYear(year, pageable);

        model.addAttribute("members", membersPage.getContent());
        model.addAttribute("totalPages", membersPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("year", year);
        model.addAttribute("sortDirection", sortDirection); // 현재 정렬 방향 추가

        return "kim/adminPage/customer/CustomerList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/CustomerWrite")
    public String showCustomerCreateForm() {
        return "kim/adminPage/customer/CustomerWrite";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/CustomerWrite")
    public String createCustomer(@ModelAttribute MemberDTO memberDTO) {
        adminCustomerService.createCustomer(memberDTO);
        return "redirect:/adminPage/CustomerList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/CustomerModify")
    public String updateCustomerForm(@RequestParam Long id, Model model) {
        Member member = adminCustomerService.findById(id);
        model.addAttribute("member", member);
        return "kim/adminPage/customer/CustomerModify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/CustomerModify")
    public String updateCustomer(@ModelAttribute Member member) {
        adminCustomerService.updateCustomer(member);
        return "redirect:/adminPage/CustomerList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/CustomerDelete")
    public String deleteCustomer(@RequestParam Long id) {
        adminCustomerService.deleteCustomer(id);
        return "redirect:/adminPage/CustomerList";
    }

}

