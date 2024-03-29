package com.example.fitconnect.controller.chat;


import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.global.annotation.CurrentUserId;
import com.example.fitconnect.dto.chat.request.ChatMessageUpdateDto;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageUpdateService;
import java.util.List;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chatmessages")
public class ChatMessageController {

    private final ChatMessageUpdateService chatMessageUpdateService;

    private final ChatMessageDeleteService chatMessageDeleteService;

    private final ChatMessageFindService chatMessageFindService;

    @PatchMapping
    public ResponseEntity<Void> updateChatMessage(
            @RequestBody ChatMessageUpdateDto updateDto, @CurrentUserId Long userId) {
        chatMessageUpdateService.updateMessage(updateDto, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteChatMessage(@PathVariable Long messageId,
            @CurrentUserId Long userId) {
        chatMessageDeleteService.deleteMessage(messageId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<ChatMessageResponseDto>> getChatRoomList(
            @RequestParam(required = false) Long chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageFindService.findChatMessagesByChatRoomId(
                chatRoomId);
        List<ChatMessageResponseDto> chatMessageResponseDtos = chatMessages.stream()
                .map(ChatMessageResponseDto::toDto).toList();
        return ResponseEntity.ok(chatMessageResponseDtos);
    }
}


