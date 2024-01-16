package com.example.fitconnect.service.user;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.dto.UserRegistrationDto;
import com.example.fitconnect.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;


    @Transactional
    public User registerNewUser(UserRegistrationDto registrationDto) {
        checkIfUserAlreadyExists(registrationDto.getEmail());

        User newUser = registrationDto.toEntity();

        try {
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.REGISTRATION_FAILED);
        }
    }

    private void checkIfUserAlreadyExists(String email) {
        userRepository.findByUserBaseInfo_Email(email).ifPresent(user -> {
            throw new BusinessException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        });
    }
}

