package com.example.fitconnect.service.chat.chatMessage;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageFindService {

    private final ChatMessageRepository chatMessageRepository;

    public Optional<ChatMessage> findChatMessageByChatMessageId(Long chatMessageId) {

        return chatMessageRepository.findById(chatMessageId);
    }

    public List<ChatMessage> findChatMessagesByChatRoomId(Long chatRoomId) {
        List<ChatMessage> messages = chatMessageRepository.findMessagesByChatRoomId(
                chatRoomId);
        return messages;
    }

    
}
