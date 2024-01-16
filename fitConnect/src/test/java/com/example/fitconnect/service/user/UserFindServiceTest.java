package com.example.fitconnect.service.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.user.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserFindServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserFindService userFindService;

    @Test
    public void findUserByEmailReturnsUser() {
        String existingEmail = "existing@example.com";
        User mockUser = new User(); // User 객체 생성 로직 추가
        when(userRepository.findByUserBaseInfo_Email(existingEmail)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userFindService.findUserByEmail(existingEmail);

        assertThat(result.isPresent()).isTrue();
        assertThat(mockUser).isEqualTo(result.get());
    }

    @Test
    public void findUserByEmailReturnsEmpty() {
        String nonExistingEmail = "nonexisting@example.com";
        when(userRepository.findByUserBaseInfo_Email(nonExistingEmail)).thenReturn(Optional.empty());

        Optional<User> result = userFindService.findUserByEmail(nonExistingEmail);

        assertThat(result.isPresent()).isFalse();
    }
}