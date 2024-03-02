package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomFindService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoom> findChatRoomByChatRoomId(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId);
    }

    public Page<ChatRoom> getChatMessages( Long userId, Pageable pageable) {
        return chatRoomRepository.findByChatRoomId( userId, pageable);
    }
    public ChatRoom findChatRoomDetail(Long chatRoomId){
        return findChatRoomByChatRoomId(chatRoomId).orElseThrow(()-> new EntityNotFoundException(
                ErrorMessages.CHATROOM_NOT_FOUND));
    }
}
