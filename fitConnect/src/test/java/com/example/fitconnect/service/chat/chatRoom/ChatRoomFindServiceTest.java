package com.example.fitconnect.service.chat.chatRoom;

import static org.assertj.core.api.Assertions.*;


import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatRoomFindServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomFindService chatRoomFindService;

    @Test
    void getChatMessages() {
        Long chatRoomId = 1L;
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        List<ChatRoom> mockMessages = Arrays.asList(new ChatRoom(), new ChatRoom());
        Page<ChatRoom> expectedPage = new PageImpl<>(mockMessages, pageable,
                mockMessages.size());

        when(chatRoomRepository.findByChatRoomId(userId, pageable)).thenReturn(
                expectedPage);

        Page<ChatRoom> result = chatRoomFindService.getChatMessages(userId, pageable);

        assertThat(result).isNotNull();
        assertThat(expectedPage).isEqualTo(result);
        verify(chatRoomRepository).findByChatRoomId(userId, pageable);
    }

    @Test
    void findChatRoomDetail_Success() {
        Long chatRoomId = 1L;
        ChatRoom chatRoom = new ChatRoom();
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));

        ChatRoom result = chatRoomFindService.findChatRoomDetail(chatRoomId);

        assertThat(result).isNotNull();
        assertThat(chatRoom).isEqualTo(result);
    }

    @Test
    void ChatRoom_NOT_FOUND() {
        Long chatRoomId = 1L;
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chatRoomFindService.findChatRoomDetail(chatRoomId))
                .isInstanceOf(EntityNotFoundException.class);
    }

}
