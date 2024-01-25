package com.example.fitconnect.controller.chat;


import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomRegistrationDto;
import com.example.fitconnect.domain.chat.dto.ChatRoomUpdateDto;
import com.example.fitconnect.service.chat.ChatRoomCreationService;
import com.example.fitconnect.service.chat.ChatRoomUpdateService;
import jakarta.servlet.http.HttpSession;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomCreationService chatRoomCreationService;

    private final ChatRoomUpdateService chatRoomUpdateService;
    private final CommonService commonService;

    @PostMapping
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestBody ChatRoomRegistrationDto registrationDto, HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        ChatRoom chatRoom = chatRoomCreationService.createChatRoom(registrationDto, userId);
        return ResponseEntity.ok(chatRoom);
    }

    @PutMapping
    public ResponseEntity<Status> updateChatRoom(@RequestBody ChatRoomUpdateDto chatRoomUpdateDto,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        chatRoomUpdateService.updateTitle(chatRoomUpdateDto,userId);
        return ResponseEntity.ok().build();
    }

}
