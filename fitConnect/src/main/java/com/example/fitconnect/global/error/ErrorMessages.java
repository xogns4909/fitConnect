package com.example.fitconnect.global.error;

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

    REVIEW_NOT_FOUND("리뷰를 찾을 수 없습니다."),

    CHATROOM_NOT_FOUND("채팅방을 찾을 수 없습니다."),

    CHAT_MESSAGE_NOT_FOUND("메세지를 찾을 수 없습니다."),


    REGISTRATION_NOT_FOUND("신청현황을 찾을 수 없습니다."),
    UNAUTHORIZED_USER("수정 권한이 없습니다."),

    INVALID_CONTENT("내용을 100글자 이하여야 합니다"),

    INVALID_RATING ("레이팅은 1점이상 5점 이하여야 합니다"),

    INVALID_MESSAGE_TIME("메세지 작성 5분이상 지났습니다"),

    ALREADY_REGISTERED("이미 이 이벤트에 신청하였습니다."),
    ORGANIZER_CANNOT_REGISTER("주최자는 자신의 이벤트에 신청할 수 없습니다."),

    CHATROOM_ALREADY_EXISTS("이미 해당 이벤트에 대한 채팅방이 존재합니다."),
    UNAUTHORIZED_CREATE_CHATROOM("채팅방 생성 권한이 없습니다."),

    EVENT_MAX_PARTICIPANTS_REACHED ("이벤트 참가자 수가 최대치에 도달했습니다."),

    Review_AlREADY_WRITTEN("전에 작성한 리뷰가 있습니다."),
    REGISTRATION_PERIOD_CLOSED ("신청 가능 시간이 아닙니다.");


    private final String message;

}