package com.example.fitconnect.service.user;

import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.user.request.UserRegistrationDto;
import com.example.fitconnect.repository.user.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegisterService userRegisterService;

    @ParameterizedTest
    @CsvSource({
            "user1@example.com, User1, http://example.com/profile1.jpg",
            "user2@example.com, User2, http://example.com/profile2.jpg"
    })
    void registerNewUser_SuccessTest(String email, String nickname, String profilePictureUrl) {
        UserRegistrationDto registrationDto = new UserRegistrationDto(email, nickname,
                profilePictureUrl);

        given(userRepository.save(any(User.class))).willAnswer(
                invocation -> invocation.getArgument(0));

        User registeredUser = userRegisterService.registerUser(registrationDto);

        UserBaseInfo userBaseInfo = registeredUser.getUserBaseInfo();
        assertThat(registeredUser).isNotNull();
        assertThat(userBaseInfo.getEmail()).isEqualTo(email);
        assertThat(userBaseInfo.getNickname()).isEqualTo(nickname);
        assertThat(userBaseInfo.getProfilePictureUrl()).isEqualTo(profilePictureUrl);
        assertThat(registeredUser.getRole()).isEqualTo(Role.MEMBER);
    }

    @ParameterizedTest
    @CsvSource({
            "fail1@example.com, FailUser1, http://example.com/failprofile1.jpg",
            "fail2@example.com, FailUser2, http://example.com/failprofile2.jpg"
    })
    void registerNewUser_FailureTest(String email, String nickname, String profilePictureUrl) {
        UserRegistrationDto registrationDto = new UserRegistrationDto(email, nickname,
                profilePictureUrl);
        given(userRepository.save(any(User.class))).willThrow(RuntimeException.class);

        assertThatThrownBy(() -> userRegisterService.registerUser(registrationDto))
                .isInstanceOf(BusinessException.class);
    }

}