package org.iclass.ProjectInsurance.test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test.html";
    }
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

}
