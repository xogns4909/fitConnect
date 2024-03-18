package com.example.fitconnect.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class CustomSessionAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isPublicResource(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticateSession(request)) {
            filterChain.doFilter(request, response);
        } else {
            log.info("401 : {}",request.getRequestURI());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isPublicResource(String requestURI) {
        return requestURI.startsWith("/api/auth/google") ||
                requestURI.startsWith("/login") ||
                requestURI.startsWith("/static/") ||
                requestURI.equals("/") ||
                requestURI.matches(".*\\.(html|js|css|jpg|jpeg|png|gif|svg)$") ||
                requestURI.startsWith("/oauth2/") ||
                requestURI.startsWith("/favicon.ico") ||
                requestURI.startsWith("manifest.json ");
    }


    private boolean authenticateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return false;
        }

        Long userId = (Long) session.getAttribute("userId");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}