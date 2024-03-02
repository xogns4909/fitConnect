package com.example.fitconnect.repository.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChatRoomRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void findByUserIdAndExerciseEventId_WhenChatRoomExists() {

        User user = new User(new UserBaseInfo("teest1123@email.com", "test", "testurl"),
                Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = new ExerciseEvent();
        entityManager.persist(event);

        ChatRoom chatRoom = new ChatRoom("title", event, user, user);
        entityManager.persist(chatRoom);

        entityManager.flush();

        Optional<ChatRoom> foundChatRoom = chatRoomRepository.findByUserIdAndExerciseEventId(
                user.getId(), event.getId());

        assertThat(foundChatRoom).isPresent();
        assertThat(foundChatRoom.get()).isEqualTo(chatRoom);
    }

    @Test
    void findByUserIdAndExerciseEventId_WhenChatRoomDoesNotExist() {
        Optional<ChatRoom> foundChatRoom = chatRoomRepository.findByUserIdAndExerciseEventId(999L,
                999L);

        assertThat(foundChatRoom).isNotPresent();
    }

}