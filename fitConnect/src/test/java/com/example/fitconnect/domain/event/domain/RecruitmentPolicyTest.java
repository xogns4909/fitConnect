package com.example.fitconnect.domain.event.domain;

import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.global.exception.BusinessException;
import java.time.LocalDateTime;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RecruitmentPolicyTest {


    @ParameterizedTest
    @MethodSource("provideRegistrationPolicy")
    void createSuccess(int maxParticipants, LocalDateTime registrationStart,
            LocalDateTime registrationEnd) {
        // When
        RecruitmentPolicy policy = new RecruitmentPolicy(maxParticipants, registrationStart,
                registrationEnd);

        // Then
        assertThat(policy).isNotNull();
        assertThat(policy.getMaxParticipants()).isEqualTo(maxParticipants);
        assertThat(policy.getRegistrationStart()).isEqualTo(registrationStart);
        assertThat(policy.getRegistrationEnd()).isEqualTo(registrationEnd);
    }

    private static Stream<Arguments> provideRegistrationPolicy() {
        return Stream.of(
                Arguments.of(50, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                Arguments.of(10, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(5))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidParticipants")
    void MaxParticipantValid(int maxParticipants, LocalDateTime registrationStart,
            LocalDateTime registrationEnd) {
        assertThatThrownBy(
                () -> new RecruitmentPolicy(maxParticipants, registrationStart, registrationEnd))
                .isInstanceOf(BusinessException.class);
    }

    private static Stream<Arguments> provideInvalidParticipants() {
        return Stream.of(
                Arguments.of(0, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                Arguments.of(-1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
                Arguments.of(101, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTime")
    void createRegistrationPolicy_TimeFailure(int maxParticipants, LocalDateTime registrationStart,
            LocalDateTime registrationEnd) {
        assertThatThrownBy(
                () -> new RecruitmentPolicy(maxParticipants, registrationStart, registrationEnd))
                .isInstanceOf(BusinessException.class);
    }

    private static Stream<Arguments> provideInvalidTime() {
        return Stream.of(
                Arguments.of(50, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(1)),
                Arguments.of(30, LocalDateTime.now(), LocalDateTime.now().minusDays(1))
        );
    }
}