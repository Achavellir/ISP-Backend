package org.isp.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Add request ID to MDC
            MDC.put("requestId", UUID.randomUUID().toString());
            
            // Add client IP to MDC
            MDC.put("clientIp", request.getRemoteAddr());
            
            // Add user ID to MDC if authenticated
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                MDC.put("userId", authentication.getName());
            }

            long startTime = System.currentTimeMillis();
            
            // Log the request
            logger.info("Request: {} {} from IP: {}", 
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr());

            // Continue with the request
            filterChain.doFilter(request, response);

            // Log the response
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Response: {} {} - Status: {} - Duration: {}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration);
        } finally {
            // Clear MDC
            MDC.clear();
        }
    }
}
