package com.example.fitconnect.domain.chat;

import static org.junit.jupiter.api.Assertions.*;


import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChatRoomTest {

    @Test
    public void createChatRoomTest() {
        ExerciseEvent mockEvent = new ExerciseEvent();
        User mockCreator = new User();
        User mockParticipant = new User();

        ChatRoom chatRoom = new ChatRoom(mockEvent, mockCreator, mockParticipant);

        assertThat(chatRoom).isNotNull();
        assertThat(chatRoom.getExerciseEvent()).isEqualTo(mockEvent);
        assertThat(chatRoom.getCreator()).isEqualTo(mockCreator);
        assertThat(chatRoom.getParticipant()).isEqualTo(mockParticipant);
    }

}
