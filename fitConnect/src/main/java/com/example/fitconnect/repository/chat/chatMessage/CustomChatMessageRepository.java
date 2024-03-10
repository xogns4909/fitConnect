package com.example.fitconnect.repository.chat.chatMessage;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import java.util.Optional;

public interface CustomChatMessageRepository {

    Optional<ChatMessage> findLastMessageByChatRoomId(Long chatRoomId);
}
