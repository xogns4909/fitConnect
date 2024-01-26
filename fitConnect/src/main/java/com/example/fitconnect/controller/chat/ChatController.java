package com.example.fitconnect.controller.chat;


import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.dto.ChatMessageRegistrationDto;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageCreationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageCreationService chatMessageCreationService;

    private final CommonService commonService;
    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/{chatRoomId}")
    public ResponseEntity<ChatMessage> sendMessage(@DestinationVariable String chatRoomId,
            ChatMessageRegistrationDto chatMessageRegistrationDto, HttpSession session) {
        log.info("Received message in chat room {}: {}", chatRoomId,chatMessageRegistrationDto);
        Long userId = commonService.extractUserIdFromSession(session);
        ChatMessage chatMessage = chatMessageCreationService.createChatMessage(
                chatMessageRegistrationDto, userId);
        return ResponseEntity.ok().body(chatMessage);
    }
}

