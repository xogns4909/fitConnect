package com.example.fitconnect.service.chat.chatMessage;

import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.chat.request.ChatMessageRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.repository.chat.chatMessage.ChatMessageRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.user.UserFindService;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
        Long userId = 1L;
        ChatMessageRegistrationDto dto = new ChatMessageRegistrationDto("테스트 메시지");
        User mockUser = new User(new UserBaseInfo("test@naver.com","test","url"), Role.MEMBER);
        ChatRoom mockChatRoom = new ChatRoom();
        ChatMessage mockChatMessage = new ChatMessage("테스트 메시지", mockChatRoom, mockUser);

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(mockUser));
        when(chatRoomFindService.findChatRoomByChatRoomId(any(Long.class))).thenReturn(
                Optional.of(mockChatRoom));
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(mockChatMessage);

        ChatMessageResponseDto createdChatMessage = chatMessageCreationService.createChatMessage(
                "message", 1L, userId);

        assertThat(createdChatMessage).isNotNull();
        assertThat(createdChatMessage.getContent()).isEqualTo(mockChatMessage.getContent());
    }

    @Test
    public void testCreateChatMessageFail_UserNotFound() {
        Long userId = 1L;
        ChatMessageRegistrationDto dto = new ChatMessageRegistrationDto("테스트 메시지");

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> chatMessageCreationService.createChatMessage("message", 1L, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testCreateChatMessageFail_ChatRoomNotFound() {
        Long userId = 1L;
        ChatMessageRegistrationDto dto = new ChatMessageRegistrationDto("테스트 메시지");
        User mockUser = new User();

        when(userFindService.findUserByUserId(userId)).thenReturn(Optional.of(mockUser));
        when(chatRoomFindService.findChatRoomByChatRoomId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> chatMessageCreationService.createChatMessage("message", 1L, userId))
                .isInstanceOf(EntityNotFoundException.class);
    }




}
