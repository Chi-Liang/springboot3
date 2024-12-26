package com.springboot3.template.handler;

import com.springboot3.template.constants.ApiConstant;
import com.springboot3.template.exception.NotFoundException;
import com.springboot3.template.model.entity.LogSysAlertTrack;
import com.springboot3.template.service.LogService;
import com.springboot3.template.utils.HttpTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = ApiConstant.API_BASE_PACKAGES)
@RequiredArgsConstructor
public class GlobalApiExceptionHandler {

    private final LogService logService;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String defaultMessages = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));

        log.error("MethodArgumentNotValidException {} :", defaultMessages);
        saveLogSysAlertTrack(e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }


    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFoundExceptionHandler(NotFoundException e) {
        log.error("NotFoundException {} :", e.getBody(),e);
        saveLogSysAlertTrack(e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail exceptionHandler(Exception e) throws IOException {
        log.error("exceptionHandler {} :", e.getMessage(),e);
        saveLogSysAlertTrack(e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    private void saveLogSysAlertTrack(Exception e) {
        LogSysAlertTrack logSysAlertTrack = LogSysAlertTrack.builder()
                .alertMsg(ExceptionUtils.getStackTrace(e))
                .alertUri(HttpTools.getQueryUri())
                .userIp(HttpTools.getIp())
                .build();
        logService.loggingAlert(logSysAlertTrack);
    }

}
