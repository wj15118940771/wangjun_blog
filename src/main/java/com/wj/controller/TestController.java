package com.wj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String index(){
//        int i = 9/0;
//        System.out.println("123456");
        return "redirect:/index";
    }
}
