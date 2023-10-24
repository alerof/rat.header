package com.alaerof.rat.header.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class CorrelationIdFilter implements Filter {
    private final String correlationIdHeader = "correlationId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            var correlationId = httpRequest.getHeader(correlationIdHeader);
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = UUID.randomUUID().toString();
                DynamicHeaderRequestWrapper requestWrapper = new DynamicHeaderRequestWrapper(httpRequest);
                requestWrapper.addHeader(correlationIdHeader, UUID.randomUUID().toString());
                log.info("Header correlationId was absent initially {}", correlationId);
                httpRequest = requestWrapper;
            }
            MDC.put(correlationIdHeader, correlationId);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.addHeader(correlationIdHeader, correlationId);
            chain.doFilter(httpRequest, response);
        } finally {
            MDC.clear();
        }
    }

    private static class DynamicHeaderRequestWrapper extends HttpServletRequestWrapper {
        private final Map<String, String> headerMap = new HashMap<>();

        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        public DynamicHeaderRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            HttpServletRequest request = (HttpServletRequest) getRequest();
            List<String> list = Collections.list(request.getHeaderNames());
            list.addAll(headerMap.keySet());
            return Collections.enumeration(list);
        }

        @Override
        public String getHeader(String name) {
            if(headerMap.containsKey(name)){
                return headerMap.get(name);
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> list = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                list.add(headerMap.get(name));
            }
            return Collections.enumeration(list);
        }
    }

}
