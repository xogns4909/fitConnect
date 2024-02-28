package com.example.fitconnect.service.user;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.user.request.UserRegistrationDto;
import com.example.fitconnect.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;


    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        User newUser = registrationDto.toEntity();
        try {
            return userRepository.save(newUser);
        }catch (RuntimeException e){
            throw new BusinessException(ErrorMessages.REGISTRATION_FAILED);
        }
    }
}

