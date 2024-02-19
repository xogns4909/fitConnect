package com.example.fitconnect.dto.user.request;

import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String nickname;
    private String profilePictureUrl;
    private Role role;

    public UserRegistrationDto(String email, String nickname, String profilePictureUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profilePictureUrl = profilePictureUrl;
        this.role = Role.MEMBER;
    }

    public User toEntity() {
        UserBaseInfo userBaseInfo = new UserBaseInfo(email, nickname, profilePictureUrl);
        return new User(userBaseInfo, role);
    }
}
