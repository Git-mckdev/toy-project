package com.overflow.toy_project.controller.main.github;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Member;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/main/github")
public class GitHubController {
    
    // GitHub 기능
    @GetMapping
    public String redirectGitHub(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");

        return "redirect:https://github.com/" + member.getGitHub();
    }
}