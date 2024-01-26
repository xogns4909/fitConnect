package com.example.fitconnect.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageRegistrationDto {
    private Long chatRoomId;
    private String content;

    public ChatMessageRegistrationDto(Long chatRoomId, String content) {
        this.chatRoomId = chatRoomId;
        this.content = content;
    }
}
