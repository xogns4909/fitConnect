package com.example.fitconnect.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageUpdateDto {

    private String content;

    private Long chatMessageId;

    public ChatMessageUpdateDto(String content,Long chatMessageId){
        this.content = content;
        this.chatMessageId = chatMessageId;
    }
}
