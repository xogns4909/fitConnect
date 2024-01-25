package com.example.fitconnect.service.chat;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomUpdateDto;
import com.example.fitconnect.repository.chat.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomUpdateService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void updateTitle(ChatRoomUpdateDto chatRoomUpdateDto) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomUpdateDto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CHATROOM_NOT_FOUND));

        chatRoom.update(chatRoomUpdateDto.getTitle());
    }
}