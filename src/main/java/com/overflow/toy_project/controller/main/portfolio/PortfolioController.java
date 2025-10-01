package com.overflow.toy_project.controller.main.portfolio;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.security.CustomUserDetails;

@Controller
@RequestMapping("/main/portfolio")
public class PortfolioController {
    
    // 포트폴리오 기능
    @GetMapping
    public String redirectPortfolio(Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        return "redirect:" + member.getPortfolio();
    }
}