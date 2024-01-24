package com.example.fitconnect.config.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.exception.BusinessException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpSession;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CommonService commonService;

    @ParameterizedTest
    @MethodSource("provideData")
    void extractUserId_Success(String accessToken, Long expectedUserId) {
        given(session.getAttribute("accessToken")).willReturn(accessToken);
        given(jwtService.getUserIdByParseToken(accessToken)).willReturn(expectedUserId);

        Long userId = commonService.extractUserIdFromSession(session);

        assertThat(userId).isEqualTo(expectedUserId);
    }

    @Test
    void extractUserId_NoToken() {
        given(session.getAttribute("accessToken")).willReturn(null);
        given(jwtService.getUserIdByParseToken(null)).willThrow(new JwtException("Invalid token"));

        assertThatThrownBy(() -> commonService.extractUserIdFromSession(session))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void extractUserId_InvalidToken() {
        String invalidToken = "invalidToken";
        given(session.getAttribute("accessToken")).willReturn(invalidToken);
        given(jwtService.getUserIdByParseToken(invalidToken)).willThrow(RuntimeException.class);

        assertThatThrownBy(() -> commonService.extractUserIdFromSession(session))
                .isInstanceOf(BusinessException.class);
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of("validToken1", 1L),
                Arguments.of("validToken2", 2L)
        );
    }

}