package com.overflow.toy_project.controller.main.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Member;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/main/portfolio")
public class PortfolioController {
    
    // 포트폴리오 기능
    @GetMapping
    public String redirectPortfolio(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");

        return "redirect:" + member.getPortfolio();
    }
}