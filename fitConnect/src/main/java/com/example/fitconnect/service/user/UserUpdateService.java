package com.example.fitconnect.service.user;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.user.request.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserFindService userFindService;

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto, Long userId) {
        User user = userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessages.USER_NOT_FOUND));

        user.update(userUpdateDto.getNickname(), userId);
    }

}
