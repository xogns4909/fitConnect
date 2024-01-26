package com.example.fitconnect.service.chat.chatMessage;

import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatMessageFindServiceTest {

    @InjectMocks
    private ChatMessageFindService chatMessageFindService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Test
    void findChatMessageByChatMessageId_WhenMessageExists() {
        Long chatMessageId = 1L;
        ChatMessage mockMessage = new ChatMessage();

        when(chatMessageRepository.findById(chatMessageId)).thenReturn(Optional.of(mockMessage));

        Optional<ChatMessage> foundMessage = chatMessageFindService.findChatMessageByChatMessageId(
                chatMessageId);

        assertThat(foundMessage).isNotEmpty();
    }

    @Test
    void findChatMessageByChatMessageId_WhenMessageDoesNotExist() {
        Long chatMessageId = 2L;
        when(chatMessageRepository.findById(chatMessageId)).thenReturn(Optional.empty());

        Optional<ChatMessage> foundMessage = chatMessageFindService.findChatMessageByChatMessageId(
                chatMessageId);

        assertThat(foundMessage).isEmpty();
    }
}
