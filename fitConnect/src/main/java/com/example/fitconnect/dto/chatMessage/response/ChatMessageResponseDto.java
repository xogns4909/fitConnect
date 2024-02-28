package com.example.fitconnect.dto.chatMessage.response;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
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

    public static ChatMessageResponseDto toDto(ChatMessage chatMessage){
        ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto();
        chatMessageResponseDto.id = chatMessage.getId();
        chatMessageResponseDto.content = chatMessage.getContent();
        chatMessageResponseDto.userId = chatMessage.getSender().getId();
        chatMessageResponseDto.senderName = chatMessage.getSender().getUserBaseInfo().getNickname();
        chatMessageResponseDto.sentAt = chatMessage.getCreatedAt();
        return  chatMessageResponseDto;
    }

}