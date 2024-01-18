package com.example.fitconnect.auth.dto;


import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;

import com.example.fitconnect.domain.user.dto.UserRegistrationDto;
import lombok.Data;

@Data
public class GoogleInfoDto {

    private String email;
    private String nickname;
    private String profilePictureUrl;
    private Role role;

    public GoogleInfoDto(String email, String nickname, String profilePictureUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profilePictureUrl = profilePictureUrl;
        this.role = Role.MEMBER;
    }

    public UserRegistrationDto toUserRegisterDto(){
     return   new UserRegistrationDto(email, nickname, profilePictureUrl);
    }
}
