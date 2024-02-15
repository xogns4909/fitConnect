package com.example.fitconnect.repository.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomChatRoomRepository {

    Page<ChatRoom> findByChatRoomId(Long userId, Pageable pageable);

    Optional<ChatRoom> findByUserIdAndExerciseEventId(Long userId, Long exerciseEventId);

}

