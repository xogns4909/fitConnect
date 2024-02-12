package com.example.fitconnect.dto.chatMessage.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ChatMessageResponseDto {

    private Long id;
    private String content;

    private Long userId;
    private String senderName;
    private LocalDateTime sentAt;

    protected ChatMessageResponseDto() {
    }

    public ChatMessageResponseDto(Long id, String content,Long userId, String senderName,
            LocalDateTime sentAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.senderName = senderName;
        this.sentAt = sentAt;
    }

}