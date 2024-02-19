package com.example.fitconnect.controller.chat;


import com.example.fitconnect.dto.chat.request.ChatMessageRegistrationDto;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageCreationService chatMessageCreationService;

    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/{chatRoomId}")
    public ResponseEntity<ChatMessageResponseDto> sendMessage(
            SimpMessageHeaderAccessor headerAccessor,
            ChatMessageRegistrationDto dto, @DestinationVariable Long chatRoomId) {

        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        ChatMessageResponseDto chatMessageResponseDto = chatMessageCreationService.createChatMessage(
                dto.getContent(), chatRoomId, userId);

        return ResponseEntity.ok().body(chatMessageResponseDto);
    }
}

