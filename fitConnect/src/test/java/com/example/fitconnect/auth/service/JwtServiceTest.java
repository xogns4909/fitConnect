package com.example.fitconnect.auth.service;


import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;



    private static Stream<User> createUser() {
        return Stream.of(
                new User(new UserBaseInfo("user1@example.com", "User1",
                        "http://example.com/profile1.jpg"), Role.MEMBER),
                new User(new UserBaseInfo("user2@example.com", "User2",
                        "http://example.com/profile2.jpg"),Role.MEMBER),
                new User(new UserBaseInfo("user3@example.com", "User3",
                        "http://example.com/profile3.jpg"),Role.MEMBER)
        );
    }

    @ParameterizedTest
    @MethodSource("createUser")
    void generateAccessToken_Success(User user) {
        user.setId(1L);
        String accessToken = jwtService.generateAccessToken(user);
        Assertions.assertThat(accessToken).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("createUser")
    void validationAccessToken(User user){
        user.setId(1L);
        String validToken = jwtService.generateAccessToken(user);
        boolean isValid = jwtService.validateAccessToken(validToken);
        Assertions.assertThat(isValid).isTrue();
    }

}