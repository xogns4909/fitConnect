package com.example.fitconnect.auth.filter;


import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (isPermitAllPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            HttpSession session = request.getSession();
            String accessToken = session.getAttribute("accessToken").toString();
            String refreshToken = session.getAttribute("refreshToken").toString();
            log.info("세션 정보{}", session);
            if (accessToken != null && jwtService.validateAccessToken(accessToken)) {
                filterChain.doFilter(request, response);
            } else if (refreshToken != null) {
                String newAccessToken = jwtService.renewAccessTokenUsingRefreshToken(refreshToken);
                session.setAttribute("accessToken", newAccessToken);
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.info("어디에온 요청일까요 ?? {}", request.getRequestURI());
            throw new BusinessException(ErrorMessages.Invalid_Token);
        }
    }

    private boolean isPermitAllPath(String requestURI) {
        return requestURI.startsWith("/api/auth/google") || requestURI.startsWith("/swagger-ui")
                || requestURI.startsWith("/v3/api-docs") || requestURI.startsWith("/h2-console");
    }
}