package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class ChatRoomDeleteServiceTest {

    @Mock
    private ChatRoomFindService chatRoomFindService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatMessageFindService chatMessageFindService;

    @Mock
    private ChatMessageDeleteService chatMessageDeleteService;

    @InjectMocks
    private ChatRoomDeleteService chatRoomDeleteService;

    @Test
    void deleteChatRoom() {
        Long userId = 1L;
        Long chatRoomId = 1L;
        ChatRoom mockChatRoom = mock(ChatRoom.class);

        when(chatRoomFindService.findChatRoomByChatRoomId(chatRoomId)).thenReturn(
                java.util.Optional.of(mockChatRoom));
        when(chatMessageFindService.findChatMessagesByChatRoomId(chatRoomId)).thenReturn(
                Collections.emptyList());

        chatRoomDeleteService.deleteChatRoom(userId, chatRoomId);

        verify(chatRoomFindService).findChatRoomByChatRoomId(chatRoomId);
        verify(chatMessageDeleteService).deleteMessages(anyList());
        verify(chatRoomRepository).delete(mockChatRoom);
    }

    @Test
    void deleteChatRoom_EntityNotFound() {
        Long userId = 1L;
        Long chatRoomId = 1L;

        when(chatRoomFindService.findChatRoomByChatRoomId(chatRoomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatRoomDeleteService.deleteChatRoom(userId, chatRoomId))
                .isInstanceOf(EntityNotFoundException.class);
        verify(chatRoomFindService).findChatRoomByChatRoomId(chatRoomId);
        verifyNoInteractions(chatMessageDeleteService);
        verifyNoInteractions(chatRoomRepository);
    }

    @Test
    void deleteChatRoom_UserIsNotCreator() {
        Long userId = 1L;
        Long chatRoomId = 1L;
        ChatRoom mockChatRoom = mock(ChatRoom.class);

        when(chatRoomFindService.findChatRoomByChatRoomId(chatRoomId)).thenReturn(Optional.of(mockChatRoom));
        doThrow(new BusinessException(ErrorMessages.UNAUTHORIZED_USER)).when(mockChatRoom).validateCreator(userId);

        assertThatThrownBy(() -> chatRoomDeleteService.deleteChatRoom(userId, chatRoomId))
                .isInstanceOf(BusinessException.class);

    }
}
