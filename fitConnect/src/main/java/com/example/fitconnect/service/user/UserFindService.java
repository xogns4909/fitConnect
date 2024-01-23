package com.example.fitconnect.service.user;

import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserRepository userRepository;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByUserBaseInfo_Email(email);
    }

    public Optional<User> findUserByUserId(Long userId){
        return userRepository.findById(userId);
    }

}