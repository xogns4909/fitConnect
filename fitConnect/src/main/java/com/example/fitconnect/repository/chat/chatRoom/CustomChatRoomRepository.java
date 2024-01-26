package com.example.fitconnect.repository.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomChatRoomRepository {

    Page<ChatMessage> findByChatRoomId(Long chatRoomId,Long userId, Pageable pageable);

}

