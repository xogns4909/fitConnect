package com.example.fitconnect.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.user.request.UserRegistrationDto;
import com.example.fitconnect.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserFindServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserFindService userFindService;

    @ParameterizedTest
    @ValueSource(strings = {"user@example.com", "user@example234.com", "user@example123.com"})
    void findUserByEmail_Found(String email) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(email,
                "user", ".jpg");
        User user = userRegistrationDto.toEntity();
        given(userRepository.findByUserBaseInfo_Email(userRegistrationDto.getEmail())).willReturn(
                Optional.of(user));

        Optional<User> result = userFindService.findUserByEmail(email);

        UserBaseInfo userBaseInfo = result.get().getUserBaseInfo();
        assertThat(result).isPresent();
        assertThat(userBaseInfo.getEmail()).isEqualTo(email);
    }

    @Test
    void findUserByEmail_NotFound() {
        String email = "nonexistent@example.com";
        given(userRepository.findByUserBaseInfo_Email(email)).willReturn(Optional.empty());

        Optional<User> result = userFindService.findUserByEmail(email);

        assertThat(result).isNotPresent();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void findUserByUserId_Found(Long userId) {
        User user = new User();
        user.setUserBaseInfo(new UserBaseInfo("user@example.com", "user", ".jpg"));
        user.setRole(Role.MEMBER);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        Optional<User> result = userFindService.findUserByUserId(userId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    void findUserByUserId_NotFound() {
        Long userId = 999L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        Optional<User> result = userFindService.findUserByUserId(userId);

        assertThat(result).isNotPresent();
    }
}