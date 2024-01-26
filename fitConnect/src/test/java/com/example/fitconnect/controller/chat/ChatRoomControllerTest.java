package com.example.fitconnect.controller.chat;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.dto.ChatRoomRegistrationDto;
import com.example.fitconnect.domain.chat.dto.ChatRoomUpdateDto;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomCreationService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomDeleteService;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatRoomController.class)
public class ChatRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JwtService jwtService;
    @MockBean
    private ChatRoomCreationService chatRoomCreationService;
    @MockBean
    private ChatRoomRepository chatRoomRepository;
    @MockBean
    private ChatRoomUpdateService chatRoomUpdateService;
    @MockBean
    private ChatRoomDeleteService chatRoomDeleteService;
    @MockBean
    private CommonService commonService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                        new ChatRoomController(chatRoomCreationService, chatRoomUpdateService,
                                chatRoomDeleteService,
                                commonService))
                .build();

    }

    @Test
    public void createChatRoom_Success() throws Exception {
        ChatRoomRegistrationDto registrationDto = new ChatRoomRegistrationDto("title", 1L);
        ChatRoom mockChatRoom = new ChatRoom("title", new ExerciseEvent(), new User(), new User());
        MockHttpSession session = new MockHttpSession();
        Long userId = 1L;

        given(commonService.extractUserIdFromSession(any(HttpSession.class))).willReturn(userId);
        given(chatRoomCreationService.createChatRoom(eq(registrationDto), eq(userId))).willReturn(
                mockChatRoom);

        mockMvc.perform(post("/api/chatrooms")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registrationDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateChatRoom_Success() throws Exception {

        ChatRoomUpdateDto updateDto = new ChatRoomUpdateDto("New Title", 1L);
        Long chatRoomId = 1L;
        doNothing().when(chatRoomUpdateService)
                .updateTitle(any(ChatRoomUpdateDto.class), anyLong());

        when(chatRoomRepository.findById(anyLong())).thenReturn(
                Optional.of(new ChatRoom("title", new ExerciseEvent(), new User(), new User())));

        mockMvc.perform(put("/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        verify(chatRoomUpdateService, times(1)).updateTitle(any(ChatRoomUpdateDto.class),
                anyLong());
    }

    @Test
    public void deleteChatRoom_Success() throws Exception {
        Long chatRoomId = 1L;
        Long userId = 1L;
        MockHttpSession session = new MockHttpSession();

        given(commonService.extractUserIdFromSession(any(HttpSession.class))).willReturn(userId);
        doNothing().when(chatRoomDeleteService).deleteChatRoom(userId, chatRoomId);

        mockMvc.perform(delete("/api/chatrooms/" + chatRoomId)
                        .session(session))
                .andExpect(status().isOk());

        verify(chatRoomDeleteService, times(1)).deleteChatRoom(userId, chatRoomId);
    }

}
