package com.example.fitconnect.auth.filter;


import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accessToken = session.getAttribute("accessToken").toString();
        String refreshToken = session.getAttribute("refreshToken").toString();

        if (accessToken != null && jwtService.validateAccessToken(accessToken)) {
            filterChain.doFilter(request, response);
        } else if (refreshToken != null) {
            String newAccessToken = jwtService.renewAccessTokenUsingRefreshToken(refreshToken);
            session.setAttribute("accessToken", newAccessToken);
            filterChain.doFilter(request,response);
        }else {
            throw new BusinessException(ErrorMessages.Invalid_Token);
        }


    }
}