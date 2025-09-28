package com.overflow.toy_project.controller.main;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.security.CustomUserDetails;

@Controller
@RequestMapping("/main")
public class MainController {

    // 메인 페이지 URL 설정
    @GetMapping("/index")
    public String viewMain(Model model, Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        model.addAttribute("username", member.getUsername());

        return "main/index";
    }
}