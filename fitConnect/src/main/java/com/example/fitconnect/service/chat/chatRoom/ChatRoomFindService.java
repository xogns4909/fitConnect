package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.dto.chat.response.ChatRoomResponseDto;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomFindService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageFindService chatMessageFindService;

    public Optional<ChatRoom> findChatRoomByChatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    public Page<ChatRoomResponseDto> getChatRoomList(Long userId, Pageable pageable) {
        Page<ChatRoom> chatRoomPage = chatRoomRepository.findByChatRoomId(userId, pageable);
        List<ChatRoomResponseDto> dtos = chatRoomPage.getContent().stream().map(chatRoom -> {
            Optional<ChatMessage> lastMessage = chatMessageFindService.findLastMessageByChatRoomId(
                    chatRoom.getId());
            return ChatRoomResponseDto.toDto(chatRoom, lastMessage.orElse(null));
        }).toList();

        return new PageImpl<>(dtos, pageable, chatRoomPage.getTotalElements());
    }


    public ChatRoom findChatRoomDetail(Long chatRoomId) {
        return findChatRoomByChatRoomId(chatRoomId).orElseThrow(() -> new EntityNotFoundException(
                ErrorMessages.CHATROOM_NOT_FOUND));
    }

    public List<ChatRoom> findChatRoomsByEventId(Long eventId) {
        return chatRoomRepository.findByEventId(eventId);
    }
}
