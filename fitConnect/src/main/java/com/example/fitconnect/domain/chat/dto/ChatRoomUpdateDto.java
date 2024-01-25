package com.example.fitconnect.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatRoomUpdateDto {

    String title;

    Long chatRoomId;

    public ChatRoomUpdateDto(String title,Long chatRoomId){
        this.title = title;
        this.chatRoomId = chatRoomId;
    }
}
