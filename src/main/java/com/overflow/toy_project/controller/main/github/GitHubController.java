package com.overflow.toy_project.controller.main.github;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.security.CustomUserDetails;

@Controller
@RequestMapping("/main/github")
public class GitHubController {
    
    // GitHub 기능
    @GetMapping
    public String redirectGitHub(Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        return "redirect:https://github.com/" + member.getGitHub();
    }
}