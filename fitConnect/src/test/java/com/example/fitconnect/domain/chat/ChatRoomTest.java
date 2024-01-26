package com.example.fitconnect.domain.chat;


import com.example.fitconnect.domain.chat.domain.ChatRoom;
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

        ChatRoom chatRoom = new ChatRoom("title",mockEvent, mockCreator, mockParticipant);

        assertThat(chatRoom).isNotNull();
        assertThat(chatRoom.getTitle()).isEqualTo("title");
        assertThat(chatRoom.getExerciseEvent()).isEqualTo(mockEvent);
        assertThat(chatRoom.getCreator()).isEqualTo(mockCreator);
        assertThat(chatRoom.getParticipant()).isEqualTo(mockParticipant);
    }

}
