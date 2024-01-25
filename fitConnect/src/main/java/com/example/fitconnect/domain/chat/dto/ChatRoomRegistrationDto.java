package com.example.fitconnect.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatRoomRegistrationDto {

    private String title;
    private Long exerciseEventId;
    private Long creatorId;

    public ChatRoomRegistrationDto(String title, Long exerciseEventId, Long creatorId) {
        this.title = title;
        this.exerciseEventId = exerciseEventId;
        this.creatorId = creatorId;
    }
}
