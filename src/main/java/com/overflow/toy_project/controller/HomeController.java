package com.overflow.toy_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 기본 페이지 URL 설정
    @GetMapping("/")
    public String viewSignIn() {
        return "redirect:/member/sign-in";
    }
}