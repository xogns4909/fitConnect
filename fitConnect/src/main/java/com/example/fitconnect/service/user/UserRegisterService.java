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
    public User registerOrGetUser(UserRegistrationDto registrationDto) {
        return userRepository.findByUserBaseInfo_Email(registrationDto.getEmail())
                .orElseGet(() -> {
                    User newUser = registrationDto.toEntity();
                    return userRepository.save(newUser);
                });
    }
}

