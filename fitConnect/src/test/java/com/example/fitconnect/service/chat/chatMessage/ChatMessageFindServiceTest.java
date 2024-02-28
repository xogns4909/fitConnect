package com.example.fitconnect.service.chat.chatMessage;

import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.Mockito.mock;
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

    @Test
    void findChatMessagesByChatRoomId() {
        Long chatRoomId = 1L;
        ChatMessage chatMessage = mock(ChatMessage.class);
        User user = new User();
        user.setId(1L);
        user.setUserBaseInfo(new UserBaseInfo("test123@naver.com","test",".com"));

        when(chatMessage.getContent()).thenReturn("Mocked Content");
        when(chatMessage.getSender()).thenReturn(user);
        when(chatMessage.getId()).thenReturn(1L);
        when(chatMessage.getUpdatedAt()).thenReturn(LocalDateTime.now());

        when(chatMessageRepository.findMessagesByChatRoomId(chatRoomId)).thenReturn(Arrays.asList(chatMessage));

        List<ChatMessageResponseDto> chatMessagesByChatRoomId = chatMessageFindService.findChatMessagesByChatRoomId(chatRoomId);

        assertThat(chatMessagesByChatRoomId).isNotNull();
        assertThat(chatMessagesByChatRoomId).hasSize(1);
        assertThat(chatMessagesByChatRoomId.get(0).getContent()).isEqualTo("Mocked Content");
    }

}
