package com.example.fitconnect.service.chat.chatMessage;

import static com.example.fitconnect.config.error.ErrorMessages.*;

import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.dto.chat.request.ChatMessageUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageUpdateService {

    private final ChatMessageFindService chatMessageFindService;

    @Transactional
    public void updateMessage(ChatMessageUpdateDto updateDto, Long userId) {
        ChatMessage message = chatMessageFindService.findChatMessageByChatMessageId(
                        updateDto.getChatMessageId())
                .orElseThrow(() -> new EntityNotFoundException(CHAT_MESSAGE_NOT_FOUND));


        message.update(updateDto.getContent(),userId);
    }
}


