package com.overflow.toy_project.security;

import java.io.IOException;

import org.springframework.security.web.session.InvalidSessionStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

    private final String defaultDestinationUrl;

    public CustomInvalidSessionStrategy(String defaultDestinationUrl) {
        this.defaultDestinationUrl = defaultDestinationUrl;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        if (!uri.equals("/") && !uri.equals("/member/sign-in")) {
            session.setAttribute("errorMsg", "세션이 만료되었습니다. 다시 로그인해 주세요.");
        }

        response.sendRedirect(defaultDestinationUrl);
    }
}