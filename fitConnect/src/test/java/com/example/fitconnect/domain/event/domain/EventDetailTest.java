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
public class EventDetailTest {

    @ParameterizedTest
    @MethodSource("provideValidEventDetails")
    void createEventDetail_Success(String title,String description, LocalDateTime startDate,
            LocalDateTime endDate) {

        EventDetail eventDetail = new EventDetail(title,description, startDate, endDate);

        assertThat(eventDetail).isNotNull();
        assertThat(eventDetail.getDescription()).isEqualTo(description);
        assertThat(eventDetail.getStartDate()).isEqualTo(startDate);
        assertThat(eventDetail.getEndDate()).isEqualTo(endDate);
    }

    private static Stream<Arguments> provideValidEventDetails() {
        return Stream.of(
                Arguments.of("title1","Description 1", LocalDateTime.now(), LocalDateTime.now().plusHours(2)),
                Arguments.of("title2","Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEventDetails")
    void createEventDetail_Failure(String title,String description, LocalDateTime startDate,
            LocalDateTime endDate) {

        assertThatThrownBy(() -> new EventDetail(title,description, startDate, endDate))
                .isInstanceOf(BusinessException.class);
    }

    private static Stream<Arguments> provideInvalidEventDetails() {
        return Stream.of(
                Arguments.of("title","Description 1", LocalDateTime.now().plusHours(2),
                        LocalDateTime.now()),
                Arguments.of("title","Description 2", null, LocalDateTime.now().plusDays(1))
        );
    }
}