package com.example.fitconnect.config.Convertor;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;

public class ChatMessageConvertor {

    public static ChatMessageResponseDto convertToResponseDto(ChatMessage chatMessage) {
        return new ChatMessageResponseDto(
                chatMessage.getId(),
                chatMessage.getContent(),
                chatMessage.getSender().getId(),
                chatMessage.getSender().getUserBaseInfo().getNickname(),
                chatMessage.getUpdatedAt()
        );
    }
}
