package com.example.fitconnect.repository.chat.chatMessage;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import java.util.List;
import org.apache.logging.log4j.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    @Query("SELECT cm FROM ChatMessage cm JOIN FETCH cm.sender WHERE cm.chatRoom.id = :chatRoomId order by cm.id desc")
    List<ChatMessage> findMessagesByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
