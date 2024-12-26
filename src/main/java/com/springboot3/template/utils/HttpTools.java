package com.springboot3.template.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;
import java.util.Optional;

@Configuration
public class HttpTools {

    public static String getIp() {
        HttpServletRequest request = getRequest();

        String ipAddress = request.getHeader("X-Forwarded-For");

        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    public static boolean isApiRequest() {
        return getRequest().getServletPath().replace(getContextPath(), "").startsWith("/api");
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(servletRequestAttributes).getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(servletRequestAttributes).getResponse();
    }

    public static String getQueryUri() {
        String requestURI = getRequest().getRequestURI();
        return requestURI;
    }

    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    public static String getHttpMethod() {
        return getRequest().getMethod();
    }

    public static boolean isLocal() {
        return StringUtils.equalsAny(getRequest().getRemoteAddr(), "127.0.0.1", "0:0:0:0:0:0:0:1");
    }

    public static HttpSession getSession() {
        return getRequest().getSession(Boolean.FALSE);
    }
}
