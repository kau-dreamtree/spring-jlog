package com.jlog.logging;

import java.io.IOException;
import java.util.Objects;

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

    private static final String REQUEST_LOG_FORMAT = "[Request] %s %s%s";
    private static final String RESPONSE_LOG_FORMAT = "[Response] %s %dms";

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException, ServletException {
        var requestWrapper = new ContentCachingRequestWrapper(request);
        var responseWrapper = new ContentCachingResponseWrapper(response);
        logRequest(requestWrapper);
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long duration = System.currentTimeMillis() - startTime;
        logResponse(response, duration);
        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(HttpServletRequest request) {
        var queryString = Objects.nonNull(request.getQueryString()) ? "?" + request.getQueryString() : "";
        var requestLog = String.format(REQUEST_LOG_FORMAT, request.getMethod(), request.getRequestURI(), queryString);
        logger.info(requestLog);
    }

    private void logResponse(HttpServletResponse response, long duration) {
        var responseLog = String.format(RESPONSE_LOG_FORMAT, response.getStatus(), duration);
        var httpStatusCode = HttpStatusCode.valueOf(response.getStatus());
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
