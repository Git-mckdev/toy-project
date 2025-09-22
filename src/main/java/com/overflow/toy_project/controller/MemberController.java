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

    // 회원가입, 로그인 기능
    @GetMapping("/")
    public String viewSignIn() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String viewSignUp() {
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

        return "main";
    }

    @GetMapping("/update")
    public String viewMemberUpdate(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");

        model.addAttribute("id", member.getId());
        model.addAttribute("username", member.getUsername());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("portfolio", member.getPortfolio());
        model.addAttribute("gitHub", member.getGitHub());

        return "member-update";
    }

    @PostMapping("/update")
    public String updateMember(Member member, HttpSession session) {
        memberService.updateMember(member);

        session.setAttribute("loginMember", member);

        return "main";
    }

    @PostMapping("/logout")
    public String logoutMember(Model model, HttpSession session) {
        model.addAttribute("errorMsg", "로그아웃 되었습니다. 다시 로그인 정보를 입력해 주세요.");

        session.removeAttribute("loginMember");

        return "sign-in";
    }

    // 메인 화면 기능
    @GetMapping("/main")
    public String viewMain() {
        return "main";
    }

    @GetMapping("/portfolio")
    public String redirectPortfolio(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");

        return "redirect:" + member.getPortfolio();
    }

    @GetMapping("/gitHub")
    public String redirectGitHub(HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");

        return "redirect:https://github.com/" + member.getGitHub();
    }
}