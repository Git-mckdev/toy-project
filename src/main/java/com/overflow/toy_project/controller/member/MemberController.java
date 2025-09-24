package com.overflow.toy_project.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 기능
    @GetMapping("/sign-in")
    public String viewSignIn() {
        return "member/sign-in";
    }

    @PostMapping("/sign-in")
    public String loginMember(Member member, Model model, HttpSession session) {
        Member loginMember = memberService.loginMember(member);

        if (loginMember == null) {
            model.addAttribute("email", member.getEmail());
            model.addAttribute("errorMsg", "이메일 혹은 비밀번호가 일치하지 않습니다.");

            return "member/sign-in";
        }

        session.setAttribute("loginMember", loginMember);

        return "redirect:/main/index";
    }

    @GetMapping("/sign-up")
    public String viewSignUp() {
        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerMember(Member member, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (memberService.validateDuplicateMember(member) != null) {
            model.addAttribute("username", member.getUsername());
            model.addAttribute("email", member.getEmail());
            model.addAttribute("errorMsg", "이미 가입된 이메일입니다.");

            return "member/sign-up";
        }

        memberService.saveMember(member);

        redirectAttributes.addFlashAttribute("successMsg", "회원가입에 성공하였습니다. 로그인 정보를 입력해 주세요.");

        return "redirect:/member/sign-in";
    }

    @GetMapping("/update")
    public String viewMemberUpdate(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");

        model.addAttribute("id", member.getId());
        model.addAttribute("username", member.getUsername());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("portfolio", member.getPortfolio());
        model.addAttribute("gitHub", member.getGitHub());

        return "member/update";
    }

    @PostMapping("/update")
    public String updateMember(Member member, HttpSession session) {
        memberService.updateMember(member);

        session.setAttribute("loginMember", member);

        return "redirect:/main/index";
    }

    @PostMapping("/logout")
    public String logoutMember(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("loginMember");

        redirectAttributes.addFlashAttribute("errorMsg", "로그아웃 되었습니다. 다시 로그인 정보를 입력해 주세요.");

        return "redirect:/member/sign-in";
    }
}