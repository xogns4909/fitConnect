package com.example.fitconnect.repository.chat;

import com.example.fitconnect.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

}
