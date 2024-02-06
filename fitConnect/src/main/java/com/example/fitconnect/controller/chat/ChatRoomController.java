package com.example.fitconnect.controller.chat;


import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomRegistrationDto;
import com.example.fitconnect.domain.chat.dto.ChatRoomUpdateDto;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomCreationService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomDeleteService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomUpdateService;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomCreationService chatRoomCreationService;

    private final ChatRoomUpdateService chatRoomUpdateService;
    private final ChatRoomDeleteService chatRoomDeleteService;

    private final ChatRoomFindService chatRoomFindService;

    @PostMapping
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestBody ChatRoomRegistrationDto registrationDto, @CurrentUserId Long userId) {
        ChatRoom chatRoom = chatRoomCreationService.createChatRoom(registrationDto, userId);
        return ResponseEntity.ok(chatRoom);
    }

    @PutMapping
    public ResponseEntity<Status> updateChatRoom(@RequestBody ChatRoomUpdateDto chatRoomUpdateDto,
            @CurrentUserId Long userId) {
        chatRoomUpdateService.updateTitle(chatRoomUpdateDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Status> deleteChatRoom(@PathVariable Long chatRoomId,
            @CurrentUserId Long userId) {
        chatRoomDeleteService.deleteChatRoom(userId, chatRoomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<Page<ChatRoom>> getChatRoomMessages(
            @CurrentUserId Long userId,
            Pageable pageable) {
        Page<ChatRoom> chatMessages = chatRoomFindService.getChatMessages(userId,
                pageable);
        return ResponseEntity.ok(chatMessages);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoom> getChatRoomDetail(@PathVariable Long chatRoomId) {
        ChatRoom chatRoom = chatRoomFindService.findChatRoomDetail(chatRoomId);
        return ResponseEntity.ok(chatRoom);
    }


}
