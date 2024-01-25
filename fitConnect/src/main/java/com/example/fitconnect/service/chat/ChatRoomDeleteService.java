package com.example.fitconnect.service.chat;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomDeleteService {

    private final ChatRoomFindService chatRoomFindService;

    private final ChatRoomRepository chatRoomRepository;

    public void deleteChatRoom(Long userId, Long chatRoomId) {


        ChatRoom chatRoom = chatRoomFindService.findCharRoomByChatRoomId(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CHATROOM_NOT_FOUND));

        chatRoom.validateCreator(userId);
        chatRoomRepository.delete(chatRoom);

    }

}

