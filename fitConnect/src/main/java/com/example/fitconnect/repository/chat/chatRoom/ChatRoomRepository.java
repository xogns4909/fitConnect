package com.example.fitconnect.repository.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long>,CustomChatRoomRepository {

}
