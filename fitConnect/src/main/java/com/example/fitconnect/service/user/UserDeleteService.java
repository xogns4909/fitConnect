package com.example.fitconnect.service.user;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserRepository userRepository;

    private final UserFindService userFindService;

    public void deleteUser(Long userId){
        User user = userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessages.USER_NOT_FOUND));

        user.validateUpdateOrDeleteAuth(userId);
        userRepository.delete(user);
    }

}
