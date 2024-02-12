package com.example.fitconnect.controller.chat;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.auth.service.JwtService;
import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.chat.dto.ChatMessageUpdateDto;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageUpdateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ChatMessageController(chatMessageUpdateService, chatMessageDeleteService,
                        chatMessageFindService)).build();
    }

    @Test
    void updateChatMessage_Success() throws Exception {
        Long userId = 1L;
        ChatMessageUpdateDto updateDto = new ChatMessageUpdateDto("content", 1L);

        mockMvc.perform(put("/api/chatmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteChatMessage_Success() throws Exception {
        Long messageId = 1L;
        Long userId = 1L;

        doNothing().when(chatMessageDeleteService).deleteMessage(messageId, userId);

        mockMvc.perform(delete("/api/chatmessages/" + messageId))
                .andExpect(status().isOk());

    }

}
