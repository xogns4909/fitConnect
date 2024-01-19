package com.example.fitconnect.service.user;



import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.JwtService;

import com.example.fitconnect.domain.user.domain.User;


import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


class LoginServiceTest {

    @Mock
    private UserFindService userFindService;


    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static Stream<GoogleInfoDto> googleInfoProvider() {
        return Stream.of(
                new GoogleInfoDto("user1@example.com", "User1", "http://example.com/profile1.jpg"),
                new GoogleInfoDto("user2@example.com", "User2", "http://example.com/profile2.jpg"),
                new GoogleInfoDto("user3@example.com", "User3", "http://example.com/profile3.jpg")
        );
    }

    @ParameterizedTest
    @MethodSource("googleInfoProvider")
    void processUserLoginTest(GoogleInfoDto googleInfoDto) {
        User user = googleInfoDto.toUserRegisterDto().toEntity();
        when(userFindService.findUserByEmail(googleInfoDto.getEmail()))
                .thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(user)).thenReturn("mockAccessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("mockRefreshToken");

        Map<String, String> tokens = loginService.processUserLogin(googleInfoDto);

        assertThat(tokens).isNotNull();
        assertThat(tokens.get("accessToken")).isEqualTo("mockAccessToken");
        assertThat(tokens.get("refreshToken")).isEqualTo("mockRefreshToken");
    }
}