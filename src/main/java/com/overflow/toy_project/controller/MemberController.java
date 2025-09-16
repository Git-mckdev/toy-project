package com.overflow.toy_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.service.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String pageIndex() {
        return "/sign-in";
    }

    @GetMapping("/sign-up")
    public String pageSignUp() {
        return "/sign-up";
    }

    @PostMapping("/register")
    public String registerMember(Member member, Model model) {
        if (memberService.validateDuplicateMember(member) == null) {
            memberService.saveMember(member);

            return "/main";
        }

        model.addAttribute("username", member.getUsername());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("errorMsg", "이미 가입된 이메일입니다.");

        return "/sign-up";
    }

    @PostMapping("/login")
    public String loginMember(Member member, Model model) {
        if (memberService.loginMember(member) != null) {
            return "/main";
        }

        model.addAttribute("email", member.getEmail());
        model.addAttribute("errorMsg", "이메일 혹은 비밀번호가 일치하지 않습니다.");

        return "/sign-in";
    }
}