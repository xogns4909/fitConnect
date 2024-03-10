package com.example.fitconnect.service.chat.chatMessage;


import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageDeleteService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void deleteMessage(Long messageId, Long userId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(
                        () -> new EntityNotFoundException(ErrorMessages.CHAT_MESSAGE_NOT_FOUND));

        message.validateUpdateOrDelete(userId);
        message.detachSender();
        chatMessageRepository.delete(message);
    }

    @Transactional
    public void deleteMessages(List<ChatMessage> messages){

        messages.forEach(ChatMessage::detachSender);

        chatMessageRepository.deleteAll(messages);
    }
}
