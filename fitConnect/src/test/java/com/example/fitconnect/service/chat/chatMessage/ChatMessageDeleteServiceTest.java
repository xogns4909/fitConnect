package com.example.fitconnect.service.chat.chatMessage;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.global.exception.BusinessException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageDeleteServiceTest {

    @InjectMocks
    private ChatMessageDeleteService chatMessageDeleteService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Test
    void deleteMessage_Success() {
        Long messageId = 1L;
        Long userId = 1L;
        ChatMessage message = mock(ChatMessage.class);
        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.of(message));
        doNothing().when(message).validateUpdateOrDelete(userId);

        assertThatCode(() -> chatMessageDeleteService.deleteMessage(messageId, userId))
                .doesNotThrowAnyException();

        verify(chatMessageRepository).delete(message);
    }

    @Test
    void deleteMessage_Failure_UnauthorizedUser() {
        Long messageId = 1L;
        Long userId = 2L;
        ChatMessage message = mock(ChatMessage.class);
        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.of(message));
        doThrow(new BusinessException(ErrorMessages.UNAUTHORIZED_USER)).when(message)
                .validateUpdateOrDelete(userId);

        assertThatThrownBy(() -> chatMessageDeleteService.deleteMessage(messageId, userId))
                .isInstanceOf(BusinessException.class);

        verify(chatMessageRepository, never()).delete(message);
    }

    @Test
    void deleteMessage_Failure_MessageNotFound() {
        Long messageId = 1L;
        Long userId = 1L;
        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatMessageDeleteService.deleteMessage(messageId, userId))
                .isInstanceOf(EntityNotFoundException.class);

        verify(chatMessageRepository, never()).delete(any(ChatMessage.class));
    }

    @Test
    void deleteMessages_Success() {
        ChatMessage message1 = mock(ChatMessage.class);
        ChatMessage message2 = mock(ChatMessage.class);
        List<ChatMessage> messages = List.of(message1, message2);

        chatMessageDeleteService.deleteMessages(messages);

        verify(message1).detachSender();
        verify(message2).detachSender();

        verify(chatMessageRepository).deleteAll(messages);
    }

}
