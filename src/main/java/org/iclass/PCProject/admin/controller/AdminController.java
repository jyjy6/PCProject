package org.iclass.PCProject.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Slf4j
@RequestMapping("/adminPage")
@RequiredArgsConstructor
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/adminMain")
    public String admin() {
        return "kim/adminPage/adminMain";
    }


}


