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
    NICKNAME_LENGTH_EXCEEDED("닉네임은 30자 이하여야합니다."),
    EMAIL_ALREADY_EXISTS("이미 존재하는 아이디 입니다."),
    REGISTRATION_FAILED("등록에 실패하였습니다"),
    SECURITY_EXCEPTION("인증 과정 중 보안 문제가 생겼습니다."),
    Invalid_Token("유효하지 않은 토큰 입니다"),
    ADDRESS_NULL_OR_EMPTY("주소는 필드가 비어있습니다."),
    REGISTRATION_DATE_INVALID("시작 시간은 종료시간보다 빨라야 합니다"),
    PARTICIPANTS_NUMBER_INVALID("모집 인워은 최소 1명에서 100명 사이 입니다."),
    TIME_NULL("시작시간 혹은 종료시간이 NULL 입니다."),
    USER_NOT_FOUND("회원은 찾을 수 없습니다."),
    EVENT_NOT_FOUND("글을 찾을 수 없습니다."),

    REGISTRATION_NOT_FOUND("신청현황을 찾을 수 없습니다."),
    UNAUTHORIZED_USER("수정 권한이 없습니다.");



    private final String message;

}