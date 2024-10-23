package org.iclass.PCProject.adminController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.PCProject.member.dto.MemberDTO;
import org.iclass.PCProject.member.service.MemberService;
import org.iclass.PCProject.member.entity.Member;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final MemberService memberService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/CustomerList")
    public String customerList(Model model, @RequestParam(required = false) String year,
                               @RequestParam(required = false) String createdAt) {
        List<MemberDTO> members = memberService.getAllMemberList();

        if (year != null && !year.isEmpty()) {
            int selectedYear = Integer.parseInt(year);
            members = members.stream()
                    .filter(member -> member.getCreatedAt().getYear() == selectedYear)
                    .collect(Collectors.toList());
        }
        members.sort(Comparator.comparing(MemberDTO::getCreatedAt));
        model.addAttribute("members", members);
        return "kim/adminPage/customer/CustomerList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/CustomerModify")
    public String updateCustomerForm(@RequestParam Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "kim/adminPage/customer/CustomerModify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/adminPage/CustomerModify")
    public String updateCustomer(@ModelAttribute Member member) {
        memberService.update(member);
        return "redirect:/adminPage/CustomerList";
    }
}
