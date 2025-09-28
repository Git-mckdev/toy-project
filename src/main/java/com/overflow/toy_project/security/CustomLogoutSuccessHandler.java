package com.overflow.toy_project.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final String defaultSuccessUrl;

    public CustomLogoutSuccessHandler(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            request.getSession().setAttribute("errorMsg", "로그아웃 되었습니다. 다시 로그인 정보를 입력해 주세요.");
        }
        
        response.sendRedirect(defaultSuccessUrl);
    }
}