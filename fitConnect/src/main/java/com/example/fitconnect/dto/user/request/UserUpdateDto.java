package com.example.fitconnect.dto.user.request;

import lombok.Getter;

@Getter
public class UserUpdateDto {

    String nickname;

    public UserUpdateDto(String nickname){
        this.nickname = nickname;
    }

    private UserUpdateDto(){}
}

