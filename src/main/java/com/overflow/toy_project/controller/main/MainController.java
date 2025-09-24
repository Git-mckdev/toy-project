package com.overflow.toy_project.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    // 메인 페이지 URL 설정
    @GetMapping("/index")
    public String viewMain() {
        return "main/index";
    }
}