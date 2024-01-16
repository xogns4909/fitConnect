package com.example.fitconnect.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {

    //user
    BASE_INFO_NULL("유저 기본정보가 비어있습니다"),
    ROLE_NULL("유저 권한은 비어있으면 안됩니다."),
    INVALID_EMAIL_FORMAT("유효한 이메일 형식이 아닙니다."),
    NICKNAME_LENGTH_EXCEEDED("닉네임은 30자 이하여야합니다.");

    private final String message;

}