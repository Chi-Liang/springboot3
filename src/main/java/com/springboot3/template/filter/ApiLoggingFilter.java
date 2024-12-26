package com.springboot3.template.filter;

import com.springboot3.template.model.entity.LogSysRestTrack;
import com.springboot3.template.service.LogService;
import com.springboot3.template.utils.HttpTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final String DEFAULT_TRACK_TAG = "JUNIT";
    private final LogService logService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !HttpTools.isApiRequest();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        LocalDateTime startTime = LocalDateTime.now();
        filterChain.doFilter(requestWrapper, responseWrapper);
        LocalDateTime endTime = LocalDateTime.now();
        String responseBody = new String(responseWrapper.getContentAsByteArray());
        String requestBody = new String(requestWrapper.getContentAsByteArray());
        if (StringUtils.isBlank(requestBody)) {
            requestBody = StreamUtils.copyToString(requestWrapper.getInputStream(), StandardCharsets.UTF_8);
        }

        responseWrapper.copyBodyToResponse();

        LogSysRestTrack logSysRestTrack = LogSysRestTrack.builder()
                .responseData(responseBody)
                .requestData(requestBody)
                .requestUri(HttpTools.getQueryUri())
                .startTime(startTime)
                .endTime(endTime)
                .runningTime(Duration.between(startTime, endTime).toMillis())
                .traceTag(getTraceTag(request))
                .userIp(HttpTools.getIp())
                .responseCode(getResponseCode(response))
                .build();
        logService.loggingRest(logSysRestTrack);
    }

    private String getTraceTag(HttpServletRequest request) {
        return StringUtils.defaultString(StringUtils.defaultIfBlank(
                request.getHeader("Trace-Tag"),
                StringUtils.defaultIfBlank(
                        request.getHeader("trace-tag"),
                        StringUtils.defaultIfBlank(
                                request.getHeader("traceTag"),
                                DEFAULT_TRACK_TAG
                        )
                )
        ));
    }

    private String getResponseCode(HttpServletResponse response) {
        int responseCode = response.getStatus();
        return responseCode == HttpStatus.INTERNAL_SERVER_ERROR.value() ? "1" : "0";
    }

}
