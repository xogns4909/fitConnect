package com.example.fitconnect.dto.user.response;

import com.example.fitconnect.domain.user.domain.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class UserResponseDto {


    private String nickname;

    private String email;

    public UserResponseDto(){};

    public UserResponseDto toDto(User user){

        this.nickname = user.getUserBaseInfo().getNickname();
        this.email = user.getUserBaseInfo().getEmail();
        return this;
    }

}
