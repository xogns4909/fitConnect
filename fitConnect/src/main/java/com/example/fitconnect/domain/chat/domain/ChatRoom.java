package com.example.fitconnect.domain.chat.domain;

import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 1 ,max = 100)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "exercise_event_id")
    private ExerciseEvent exerciseEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "participant_id")
    private User participant;

    public ChatRoom(String title,ExerciseEvent exerciseEvent, User creator, User participant) {
        this.title = title;
        setExerciseEvent(exerciseEvent);
        setCreator(creator);
        setParticipant(participant);
    }

    public ChatRoom() {
    }

    private void setExerciseEvent(ExerciseEvent exerciseEvent) {
        this.exerciseEvent = exerciseEvent;
        if (!exerciseEvent.getChatRooms().contains(this)) {
            exerciseEvent.getChatRooms().add(this);
        }
    }

    private  void setCreator(User creator) {
        this.creator = creator;
        if (!creator.getCreatedChatRooms().contains(this)) {
            creator.getCreatedChatRooms().add(this);
        }
    }

    private void setParticipant(User participant) {
        this.participant = participant;
        if (participant != null && !participant.getParticipatingChatRooms().contains(this)) {
            participant.getParticipatingChatRooms().add(this);
        }
    }


}
