package com.example.fitconnect.service.chat.chatMessage;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.repository.chat.ChatMessageRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageFindService {

    private final ChatMessageRepository chatMessageRepository;

    public Optional<ChatMessage> findChatMessageByChatMessageId(Long chatMessageId) {

        return chatMessageRepository.findById(chatMessageId);
    }
}
