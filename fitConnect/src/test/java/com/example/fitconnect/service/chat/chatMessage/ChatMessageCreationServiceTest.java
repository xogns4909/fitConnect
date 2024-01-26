package com.example.fitconnect.service.chat.chatMessage;

import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatMessageRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.user.UserFindService;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatMessageCreationServiceTest {

    @MockBean
    private ChatMessageRepository chatMessageRepository;

    @MockBean
    private ChatRoomFindService chatRoomFindService;

    @MockBean
    private UserFindService userFindService;

    @Autowired
    private ChatMessageCreationService chatMessageCreationService;

    @Test
    public void testCreateChatMessageSuccess() {
        // 준비
        Long userId = 1L;
        ChatMessageRegistrationDto dto = new ChatMessageRegistrationDto(1L, "테스트 메시지");
        User mockUser = new User();
        ChatRoom mockChatRoom = new ChatRoom();
        ChatMessage mockChatMessage = new ChatMessage("테스트 메시지", mockChatRoom, mockUser);

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(mockUser));
        when(chatRoomFindService.findCharRoomByChatRoomId(dto.getChatRoomId())).thenReturn(
                Optional.of(mockChatRoom));
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(mockChatMessage);

        ChatMessage createdChatMessage = chatMessageCreationService.createChatMessage(dto, userId);

        assertThat(createdChatMessage).isNotNull()
                .extracting("content", "chatRoom", "sender")
                .containsExactly(dto.getContent(), mockChatRoom, mockUser);
    }
    @Test
    public void testCreateChatMessageFail_UserNotFound() {
        Long userId = 1L;
        ChatMessageRegistrationDto dto = new ChatMessageRegistrationDto(1L, "테스트 메시지");

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatMessageCreationService.createChatMessage(dto, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    public void testCreateChatMessageFail_ChatRoomNotFound() {
        Long userId = 1L;
        ChatMessageRegistrationDto dto = new ChatMessageRegistrationDto(1L, "테스트 메시지");
        User mockUser = new User();

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(mockUser));
        when(chatRoomFindService.findCharRoomByChatRoomId(dto.getChatRoomId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatMessageCreationService.createChatMessage(dto, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
