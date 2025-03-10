package com.example.alaerof.rat.header.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@Order(2)
public class ErrorHeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.addHeader("always-rat-header", MDC.get("correlationId"));
        Optional.ofNullable(httpRequest.getHeader("err")).ifPresent(testValue -> addErrHeader(httpResponse, testValue));

        log.info("-- request --");
        Collections.list(httpRequest.getHeaderNames()).forEach(log::info);
        log.info("-- response --");
        httpResponse.getHeaderNames().forEach(log::info);

        chain.doFilter(httpRequest, httpResponse);
    }

    private void addErrHeader(HttpServletResponse httpResponse, String value){
        log.info("err: {}", value);
        httpResponse.addHeader("errorcode", value);
    }
}
