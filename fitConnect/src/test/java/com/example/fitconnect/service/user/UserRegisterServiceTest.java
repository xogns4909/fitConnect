package com.example.fitconnect.service.user;

import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.dto.UserRegistrationDto;
import com.example.fitconnect.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegisterService userRegisterService;

    private UserRegistrationDto registrationDto;

    @Test
    void registerNewUser_Success() {
        given(userRepository.save(any(User.class))).willAnswer(i -> i.getArgument(0));

        User registeredUser = userRegisterService.registerUser(registrationDto);

        assertNotNull(registeredUser);
        assertEquals("user@example.com", registeredUser.getUserBaseInfo().getEmail());
        assertEquals("TestUser", registeredUser.getUserBaseInfo().getNickname());
        assertEquals(Role.MEMBER, registeredUser.getRole());
    }
}
