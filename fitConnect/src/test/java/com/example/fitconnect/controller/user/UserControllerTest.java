package com.example.fitconnect.controller.user;

import com.example.fitconnect.auth.dto.GoogleInfoDto;
import com.example.fitconnect.auth.service.AuthService;
import com.example.fitconnect.service.user.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
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
    private ObjectMapper objectMapper = new ObjectMapper();

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
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("accessToken", "mockAccessToken");
        expectedResponse.put("refreshToken", "mockRefreshToken");

        given(authService.authenticate(token)).willReturn(mockGoogleInfo);
        given(loginService.processUserLogin(mockGoogleInfo)).willReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/google")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}