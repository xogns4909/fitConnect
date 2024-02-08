package com.example.fitconnect.controller.chat;


import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.dto.ChatMessageUpdateDto;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageUpdateService;
import com.nimbusds.oauth2.sdk.Message;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping()
    public ResponseEntity<Status> updateChatMessage(
            @RequestBody ChatMessageUpdateDto updateDto, @CurrentUserId Long userId) {
        chatMessageUpdateService.updateMessage(updateDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Status> deleteChatMessage(@PathVariable Long messageId,
            @CurrentUserId Long userId) {
        chatMessageDeleteService.deleteMessage(messageId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ChatMessageResponseDto>> getChatMessages(
            @RequestParam(required = false) Long chatRoomId) {
        List<ChatMessageResponseDto> chatMessages = chatMessageFindService.findChatMessagesByChatRoomId(
                chatRoomId);
        return ResponseEntity.ok(chatMessages);
    }
}


