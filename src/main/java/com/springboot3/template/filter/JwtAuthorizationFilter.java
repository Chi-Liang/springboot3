package com.springboot3.template.filter;

import com.springboot3.template.model.entity.LogSysAlertTrack;
import com.springboot3.template.service.LogService;
import com.springboot3.template.utils.HttpTools;
import com.springboot3.template.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final LogService logService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (!HttpTools.isApiRequest()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        validateToken(request);
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request) {
        try {
            String token = jwtUtil.resolveToken(request);
            if (Objects.isNull(token)) {
                throw new RuntimeException("Token not found");
            }
            Authentication auth = jwtUtil.createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {

            LogSysAlertTrack logSysAlertTrack = LogSysAlertTrack.builder()
                    .alertMsg(ExceptionUtils.getStackTrace(e))
                    .alertUri(HttpTools.getQueryUri())
                    .userIp(HttpTools.getIp())
                    .build();
            logService.loggingAlert(logSysAlertTrack);

            if (e instanceof ExpiredJwtException) {
                log.error("Jwt token expired : {}", e.getMessage(), e);
            } else {
                log.error("Jwt token not valid: {}", e.getMessage(), e);
            }
        }
    }
}
