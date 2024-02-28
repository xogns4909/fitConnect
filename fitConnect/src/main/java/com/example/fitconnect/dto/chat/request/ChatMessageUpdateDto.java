package com.example.fitconnect.dto.chat.request;

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
