package com.overflow.toy_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String pageIndex() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String pageSignUp() {
        return "sign-up";
    }

    @PostMapping("/register")
    public String registerMember(Member member, Model model, HttpSession session) {
        if (memberService.validateDuplicateMember(member) != null) {
            model.addAttribute("username", member.getUsername());
            model.addAttribute("email", member.getEmail());
            model.addAttribute("errorMsg", "이미 가입된 이메일입니다.");

            return "sign-up";
        }

        memberService.saveMember(member);

        session.setAttribute("loginMember", member);

        model.addAttribute("successMsg", "회원가입에 성공하였습니다. 로그인 정보를 입력해 주세요.");

        return "sign-in";
    }

    @PostMapping("/login")
    public String loginMember(Member member, Model model, HttpSession session) {
        Member loginMember = memberService.loginMember(member);

        if (loginMember == null) {
            model.addAttribute("email", member.getEmail());
            model.addAttribute("errorMsg", "이메일 혹은 비밀번호가 일치하지 않습니다.");

            return "sign-in";
        }

        session.setAttribute("loginMember", loginMember);

        return "redirect:/posts";
    }
}