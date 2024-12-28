package com.springboot3.template.security;

import com.mysql.cj.protocol.x.MessageConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.MessageUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthFailureHandler implements AuthenticationFailureHandler {

    private final String defaultFailureUrl;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String error = exception.getMessage();
        session.setAttribute("error", error);
        redirectStrategy.sendRedirect(request, response, defaultFailureUrl + "?error");
    }
}
