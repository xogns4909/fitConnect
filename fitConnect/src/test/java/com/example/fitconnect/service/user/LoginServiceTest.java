package com.example.fitconnect.service.user;


import com.example.fitconnect.auth.dto.GoogleInfoDto;

import com.example.fitconnect.domain.user.domain.User;


import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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



    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void processUserLoginTest() {
        GoogleInfoDto googleInfoDto = new GoogleInfoDto("user3@example.com", "User3",
                "http://example.com/profile3.jpg");
        User user = googleInfoDto.toUserRegisterDto().toEntity();

        when(userFindService.findUserByEmail(googleInfoDto.getEmail()))
                .thenReturn(Optional.of(user));

        Long userId = loginService.processUserLogin(googleInfoDto);

        Assertions.assertThat(user.getId()).isEqualTo(userId);
    }
}