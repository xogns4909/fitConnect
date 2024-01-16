package com.example.fitconnect.domain.user.dto;

import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationDto {
    private String email;
    private String nickname;
    private String profilePictureUrl;
    private Role role;

    public User toEntity() {
        UserBaseInfo userBaseInfo = new UserBaseInfo(email, nickname, profilePictureUrl);
        return new User(userBaseInfo, role);
    }
}
