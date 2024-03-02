package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.dto.chat.request.ChatRoomUpdateDto;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomUpdateService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomFindService chatRoomFindService;

    @Transactional
    public void updateTitle(ChatRoomUpdateDto chatRoomUpdateDto,Long userId,Long chatRoomId) {

        ChatRoom chatRoom = chatRoomFindService.findChatRoomByChatRoomId(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CHATROOM_NOT_FOUND));

        chatRoom.validateCreator(userId);
        chatRoom.update(chatRoomUpdateDto.getTitle());
    }
}
