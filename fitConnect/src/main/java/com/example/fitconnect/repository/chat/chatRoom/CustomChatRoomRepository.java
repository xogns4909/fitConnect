package com.example.fitconnect.repository.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomChatRoomRepository {

    Page<ChatRoom> findByChatRoomId(Long userId, Pageable pageable);

}

