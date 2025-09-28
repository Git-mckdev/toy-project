package com.overflow.toy_project.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final String defaultFailureUrl;

    public CustomAuthFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);

        this.defaultFailureUrl = defaultFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.getSession().setAttribute("errorMsg", "이메일 또는 비밀번호가 일치하지 않습니다.");
        
        getRedirectStrategy().sendRedirect(request, response, defaultFailureUrl);
    }
}