package com.springboot3.template.handler;

import com.springboot3.template.constants.ApiConstant;
import com.springboot3.template.exception.NotFoundException;
import com.springboot3.template.model.entity.LogSysAlertTrack;
import com.springboot3.template.service.LogService;
import com.springboot3.template.utils.HttpTools;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = ApiConstant.BACKEND_BASE_PACKAGES)
@RequiredArgsConstructor
public class GlobalBackendExceptionHandler {

    private final LogService logService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Value("${project.path}")
    private String projectPath;

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(RedirectAttributes redirectAttributes, Exception e) throws IOException {
        log.error("GlobalBackendExceptionHandler error :{}",e.getMessage(),e);
        String redirectUrl = projectPath + "/backend/internalServerError";
        ModelAndView modelAndView = new ModelAndView("redirect:" + redirectUrl);
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

}
