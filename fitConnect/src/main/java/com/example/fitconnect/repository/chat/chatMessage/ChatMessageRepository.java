package com.example.fitconnect.repository.chat.chatMessage;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

}
