package com.example.fitconnect.service.chat.chatRoom;

import static org.junit.jupiter.api.Assertions.*;


import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
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
    private ChatRoomFindService chatRoomService;

    @Test
    void getChatMessages_WhenCalledWithValidParameters() {
        Long chatRoomId = 1L;
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        List<ChatRoom> mockMessages = Arrays.asList(new ChatRoom(), new ChatRoom());
        Page<ChatRoom> expectedPage = new PageImpl<>(mockMessages, pageable,
                mockMessages.size());

        when(chatRoomRepository.findByChatRoomId(chatRoomId, userId, pageable)).thenReturn(
                expectedPage);

        Page<ChatRoom> result = chatRoomService.getChatMessages(chatRoomId, userId, pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(chatRoomRepository).findByChatRoomId(chatRoomId, userId, pageable);
    }
}
