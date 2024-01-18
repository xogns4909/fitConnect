package com.example.fitconnect.auth.service;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;



public class JwtServiceTest {

    @Mock
    private Environment env;

    @InjectMocks
    private JwtService jwtService;

    public JwtServiceTest() {
        openMocks(this);
        when(env.getProperty("jwt.secret")).thenReturn("your-secret-key");
        when(env.getProperty("jwt.expiration")).thenReturn("3600000"); // 1 hour
        when(env.getProperty("jwt.refreshExpiration")).thenReturn("604800000"); // 1 week
    }

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
    void generateRefreshToken_Success(User user) {
        user.setId(1L);
        String refreshToken = jwtService.generateAccessToken(user);
        Assertions.assertThat(refreshToken).isNotNull();
    }
}