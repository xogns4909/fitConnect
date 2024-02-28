package com.example.fitconnect.domain.registration;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "registrations")
public class Registration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference("user-registration")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_event_id")
    private ExerciseEvent exerciseEvent;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status = RegistrationStatus.APPLIED;


    public Registration(User user, ExerciseEvent exerciseEvent) {
        setUser(user);
        setExerciseEvent(exerciseEvent);
    }

    public Registration() {

    }

    private void setUser(User user) {
        this.user = user;
        if(!user.getRegistrations().contains(this)) {
            user.getRegistrations().add(this);
        }
    }
    private void setExerciseEvent(ExerciseEvent exerciseEvent) {
        this.exerciseEvent = exerciseEvent;
        if(!exerciseEvent.getRegistrations().contains(this)){
            exerciseEvent.getRegistrations().add(this);
        }
    }

    public void cancel(Long userId) {
        checkAuthentication(userId);
        this.status = RegistrationStatus.CANCELED;
    }

    public void approve() {
        this.status = RegistrationStatus.APPROVED;
    }

    public void deny(){
        this.status = RegistrationStatus.REJECTED;
    }

    private void checkAuthentication(Long userId) {
        if(user.getId() != userId){
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }
    }


}