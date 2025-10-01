package com.overflow.toy_project.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.overflow.toy_project.model.Member;
import com.overflow.toy_project.security.CustomUserDetails;
import com.overflow.toy_project.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 기능
    @GetMapping("/sign-in")
    public String viewSignIn(Model model, HttpSession session, Authentication authentication) {
        if (authentication != null
            && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/main/index";
        }
        
        String key = "errorMsg";
        Object errorMsg = session.getAttribute(key);

        if (errorMsg != null) {
            model.addAttribute(key, errorMsg);

            session.removeAttribute(key);
        }

        return "member/sign-in";
    }

    @GetMapping("/sign-up")
    public String viewSignUp() {
        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerMember(Member member, Model model, RedirectAttributes redirectAttributes) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        
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
    public String viewMemberUpdate(Model model, Authentication authentication) {
        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        model.addAttribute("id", member.getId());
        model.addAttribute("username", member.getUsername());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("portfolio", member.getPortfolio());
        model.addAttribute("gitHub", member.getGitHub());

        return "member/update";
    }

    @PostMapping("/update")
    public String updateMember(Member member, Authentication authentication) {
        Member originMember = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        originMember.setEmail(member.getEmail());
        originMember.setUsername(member.getUsername());
        System.out.println("<" + member.getPassword() + ">");
        originMember.setPassword(passwordEncoder.encode(member.getPassword()));
        originMember.setPortfolio(member.getPortfolio());
        originMember.setGitHub(member.getGitHub());

        memberService.updateMember(originMember);

        return "redirect:/main/index";
    }
}