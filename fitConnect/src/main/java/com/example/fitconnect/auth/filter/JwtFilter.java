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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isPermitAllPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            processTokenAuthentication(request);
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            log.error("Token validation error: {}", e.getMessage(), e);
            throw e; // 혹은 적절한 에러 처리
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            throw new BusinessException(ErrorMessages.Invalid_Token);
        }
    }

    private void processTokenAuthentication(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String accessToken = getTokenFromSession(session, "accessToken");
        String refreshToken = getTokenFromSession(session, "refreshToken");
        log.info("엑세스 토큰  :  {}", accessToken);
        log.info("리프레시 토큰 : {}",refreshToken);
        if (accessToken != null && jwtService.validateAccessToken(accessToken)) {
            setAuthenticationContext(jwtService.getUserIdByParseToken(accessToken));
        } else if (refreshToken != null) {
            String newAccessToken = jwtService.renewAccessTokenUsingRefreshToken(refreshToken);
            session.setAttribute("accessToken", newAccessToken);
            setAuthenticationContext(jwtService.getUserIdByParseToken(newAccessToken));
        }
    }

    private String getTokenFromSession(HttpSession session, String tokenName) {
        return  (String) session.getAttribute(tokenName);

    }

    private void setAuthenticationContext(Long userId) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean isPermitAllPath(String requestURI) {
        return  requestURI.startsWith("/api/auth/google")
                || requestURI.startsWith("/swagger-ui")
                || requestURI.startsWith("/v3/api-docs") || requestURI.startsWith("/h2-console");
    }
}
