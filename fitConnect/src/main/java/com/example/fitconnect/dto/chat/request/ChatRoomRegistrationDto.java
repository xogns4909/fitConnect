package com.example.fitconnect.dto.chat.request;

import lombok.Getter;

@Getter
public class ChatRoomRegistrationDto {

    private String title;
    private Long exerciseEventId;

    public ChatRoomRegistrationDto(String title, Long exerciseEventId) {
        this.title = title;
        this.exerciseEventId = exerciseEventId;

    }
}
