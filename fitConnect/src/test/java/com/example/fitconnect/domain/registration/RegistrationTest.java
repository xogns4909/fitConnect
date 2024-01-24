package com.example.fitconnect.domain.registration;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationCreationTest {

    @Test
    void createRegistration() {
        User user = new User();
        ExerciseEvent exerciseEvent = new ExerciseEvent();

        Registration registration = new Registration(user, exerciseEvent);

        assertThat(registration.getUser()).isEqualTo(user);
        assertThat(registration.getExerciseEvent()).isEqualTo(exerciseEvent);
    }
}