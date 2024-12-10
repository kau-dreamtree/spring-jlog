package com.jlog.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(2)
public class HttpLogFilter extends OncePerRequestFilter {

    private static final String REQUEST_LOG_FORMAT = "[Request] %s %s";
    private static final String RESPONSE_LOG_FORMAT = "[Response] %s %sms";

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        logRequest(requestWrapper);
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long duration = System.currentTimeMillis() - startTime;
        logResponse(response, duration);
    }

    private void logRequest(HttpServletRequest request) {
        String requestLog = String.format(REQUEST_LOG_FORMAT, request.getMethod(), request.getRequestURI());
        logger.info(requestLog);
    }

    private void logResponse(HttpServletResponse response, long duration) {
        String responseLog = String.format(RESPONSE_LOG_FORMAT, response.getStatus(), duration);
        HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(response.getStatus());
        if (httpStatusCode.is5xxServerError()) {
            logger.error(responseLog);
            return;
        }
        if (httpStatusCode.is4xxClientError()) {
            logger.warn(responseLog);
            return;
        }
        logger.info(responseLog);
    }
}
