package com.springboot3.template.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot3.template.utils.HttpTools;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Value("${template.entry-point.auth}")
    private String authEntryPoint;

    @Value("${project.path}")
    private String projectPath;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (HttpTools.isApiRequest()) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, authException.getMessage());
            problemDetail.setTitle(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            problemDetail.setProperty("timestamp", Instant.now());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(),problemDetail);
            return;
        }
        redirectStrategy.sendRedirect(request, response,projectPath + "/" + authEntryPoint + "/login");
    }
}
