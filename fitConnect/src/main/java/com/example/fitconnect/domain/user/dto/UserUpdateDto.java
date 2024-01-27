package com.example.fitconnect.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateDto {

    String nickname;

    public UserUpdateDto(String nickname){
        this.nickname = nickname;
    }
}
