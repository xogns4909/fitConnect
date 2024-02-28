package com.example.fitconnect.controller.chat;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.dto.chat.request.ChatRoomUpdateDto;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageUpdateService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomUpdateService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChatMessageController.class)
public class ChatMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatMessageUpdateService chatMessageUpdateService;
    @MockBean
    private ChatMessageDeleteService chatMessageDeleteService;
    @MockBean
    private ChatMessageFindService chatMessageFindService;

    @MockBean
    private ChatRoomUpdateService chatRoomUpdateService;

    @MockBean
    private ChatRoomRepository chatRoomRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ChatMessageController(chatMessageUpdateService, chatMessageDeleteService,
                        chatMessageFindService)).build();
    }

    @Test
    public void updateChatRoom_Success() throws Exception {
        ChatRoomUpdateDto updateDto = new ChatRoomUpdateDto("New Title", 1L);
        Long chatRoomId = 1L;
        doNothing().when(chatRoomUpdateService)
                .updateTitle(any(ChatRoomUpdateDto.class), anyLong(), anyLong());

        when(chatRoomRepository.findById(anyLong())).thenReturn(
                Optional.of(new ChatRoom("title", new ExerciseEvent(), new User(), new User())));

        mockMvc.perform(patch("/api/chatrooms/" + chatRoomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteChatMessage_Success() throws Exception {
        Long messageId = 1L;
        Long userId = 1L;

        doNothing().when(chatMessageDeleteService).deleteMessage(messageId, userId);

        mockMvc.perform(delete("/api/chatmessages/" + messageId))
                .andExpect(status().isNoContent());

    }

}
