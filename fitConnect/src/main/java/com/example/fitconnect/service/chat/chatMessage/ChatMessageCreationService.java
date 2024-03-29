package com.example.fitconnect.service.chat.chatMessage;


import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.user.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageCreationService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomFindService chatRoomFindService;

    private final UserFindService userFindService;

    @Transactional
    public ChatMessageResponseDto createChatMessage(String message, Long chatRoomId, Long userId) {
        ChatRoom chatRoom = findChatRoom(message, chatRoomId);
        User sender = findUser(userId);

        ChatMessage chatMessage = new ChatMessage(message, chatRoom, sender);
        ChatMessage saveChatMessage = chatMessageRepository.save(chatMessage);
        return ChatMessageResponseDto.toDto(saveChatMessage);

    }

    private User findUser(Long userId) {
        User sender = userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
        return sender;
    }

    private ChatRoom findChatRoom(String message, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomFindService.findChatRoomByChatRoomId(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CHATROOM_NOT_FOUND));
        return chatRoom;
    }
}
