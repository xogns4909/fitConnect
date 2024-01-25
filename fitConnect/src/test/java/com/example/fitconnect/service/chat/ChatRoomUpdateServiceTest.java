package com.example.fitconnect.service.chat;

import static org.junit.jupiter.api.Assertions.*;


import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomUpdateDto;
import com.example.fitconnect.repository.chat.ChatRoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChatRoomUpdateServiceTest {

    @InjectMocks
    private ChatRoomUpdateService chatRoomUpdateService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatRoom chatRoom;

    private ChatRoomUpdateDto chatRoomUpdateDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        chatRoomUpdateDto = new ChatRoomUpdateDto("update title", 1L);
    }

    @Test
    public void updateTitle_Success() {
        when(chatRoomRepository.findById(any(Long.class))).thenReturn(Optional.of(chatRoom));

        chatRoomUpdateService.updateTitle(chatRoomUpdateDto);

        verify(chatRoomRepository, times(1)).findById(any(Long.class));
        verify(chatRoom, times(1)).update(any(String.class));
    }

    @Test
    public void updateTitle_Failure() {
        when(chatRoomRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> chatRoomUpdateService.updateTitle(chatRoomUpdateDto))
                .isInstanceOf(EntityNotFoundException.class);

        verify(chatRoomRepository, times(1)).findById(any(Long.class));
        verify(chatRoom, never()).update(any(String.class));
    }
}
