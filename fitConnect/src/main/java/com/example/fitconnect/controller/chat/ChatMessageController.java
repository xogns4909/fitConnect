package com.example.fitconnect.controller.chat;


import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.chat.dto.ChatMessageUpdateDto;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageUpdateService;
import jakarta.servlet.http.HttpSession;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chatmessages")
public class ChatMessageController {

    private final ChatMessageUpdateService chatMessageUpdateService;

    private final ChatMessageDeleteService chatMessageDeleteService;
    private final CommonService commonService;

    @PutMapping("/{id}")
    public ResponseEntity<Status> updateChatMessage(
            @RequestBody ChatMessageUpdateDto updateDto, HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        chatMessageUpdateService.updateMessage(updateDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Status> deleteChatMessage(@PathVariable Long messageId,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        chatMessageDeleteService.deleteMessage(messageId, userId);
        return ResponseEntity.ok().build();
    }
}


