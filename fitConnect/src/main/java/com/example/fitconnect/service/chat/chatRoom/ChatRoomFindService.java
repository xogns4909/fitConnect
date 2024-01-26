package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
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

    public Optional<ChatRoom> findCharRoomByChatRoomId(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId);
    }

    public Page<ChatRoom> getChatMessages(Long chatRoomId, Long userId, Pageable pageable) {
        return chatRoomRepository.findByChatRoomId(chatRoomId, userId, pageable);
    }
}
