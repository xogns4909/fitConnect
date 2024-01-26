package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.ChatRoomRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomFindService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoom> findCharRoomByChatRoomId(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId);
    }

}
