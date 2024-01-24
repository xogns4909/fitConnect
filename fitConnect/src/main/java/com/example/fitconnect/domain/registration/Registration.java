package com.example.fitconnect.domain.registration;

import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "registrations")
public class Registration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_event_id")
    private ExerciseEvent exerciseEvent;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status = RegistrationStatus.APPLIED;


    public Registration(User user, ExerciseEvent exerciseEvent) {
        this.user = user;
        this.exerciseEvent = exerciseEvent;
    }

    public Registration() {

    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getRegistrations().contains(this)) {
            user.getRegistrations().add(this);
        }
    }

}