package com.example.fitconnect.domain.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ChatMessageTest {


    @Test
    public void testCreateChatMessage() {
        ChatRoom chatRoom = new ChatRoom();
        User sender = new User();

        ChatMessage chatMessage = new ChatMessage("테스트 메시지", chatRoom, sender);

        assertThat(chatMessage).isNotNull();
        assertThat(chatMessage.getContent()).isEqualTo("테스트 메시지");
        assertThat(chatMessage.getChatRoom()).isEqualTo(chatRoom);
        assertThat(chatMessage.getSender()).isEqualTo(sender);

    }
}
