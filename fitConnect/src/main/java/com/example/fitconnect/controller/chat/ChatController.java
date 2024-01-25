package com.example.fitconnect.controller.chat;

import com.example.fitconnect.domain.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/{chatRoomId}")
    public ChatMessage sendMessage(@DestinationVariable String chatRoomId, ChatMessage chatMessage) {
        log.info("Received message in chat room {}: {}", chatRoomId, chatMessage);
        return chatMessage;
    }
}

