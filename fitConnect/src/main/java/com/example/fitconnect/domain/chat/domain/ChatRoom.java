package com.example.fitconnect.domain.chat.domain;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Table(name = "chat_rooms")
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 1 ,max = 100)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseEvent exerciseEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    private User participant;

    public ChatRoom(String title,ExerciseEvent exerciseEvent,User creator,User participant) {
        this.title = title;
        this.exerciseEvent = exerciseEvent;
        this.creator = creator;
        this.participant = participant;
    }

    public ChatRoom() {
    }


    public void update(String title) {;
        this.title = title;
    }

    public void validateCreator(Long userId) {
        if (!userId.equals(this.creator.getId())) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }
    }

    public void deleteEvent(){
        this.exerciseEvent = null;
    }
}
