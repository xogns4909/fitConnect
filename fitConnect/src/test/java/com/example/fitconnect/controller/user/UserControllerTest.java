package com.example.fitconnect.controller.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.AuthService;
import com.example.fitconnect.service.user.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jdk.jshell.Snippet.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class UserControllerTest {

    @Mock
    private LoginService loginService;
    @Mock
    private AuthService authService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void loginTest() throws Exception {
        String token = "mockGoogleToken";
        GoogleInfoDto mockGoogleInfo = new GoogleInfoDto("user@example.com", "User",
                "http://example.com/profile.jpg");

        given(authService.authenticate(token)).willReturn(mockGoogleInfo);
        given(loginService.processUserLogin(mockGoogleInfo)).willReturn(1L);

        mockMvc.perform(post("/api/auth/google")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(token))
                .andExpect(status().isOk());
    }

    @Test
    void logoutTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "someUserId");

        mockMvc.perform(post("/api/auth/logout").session(session))
                .andExpect(status().isOk());

        assertThat(session.isInvalid()).isTrue();
    }

}