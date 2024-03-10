package com.example.fitconnect.domain.event.domain;

import static org.assertj.core.api.Assertions.*;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class ExerciseEventTest {

    @ParameterizedTest
    @MethodSource("provideValidExerciseEvents")
    void createExerciseEvent_Success(User organizer, EventDetail eventDetail,
            RecruitmentPolicy registrationPolicy,
            Location location, Category category) {
        List<Image> images = new ArrayList<>();
        ExerciseEvent exerciseEvent = new ExerciseEvent(organizer, eventDetail, registrationPolicy,
                location, category,images);

        assertThat(exerciseEvent).isNotNull();
        assertThat(exerciseEvent.getOrganizer()).isEqualTo(organizer);
        assertThat(exerciseEvent.getEventDetail()).isEqualTo(eventDetail);
        assertThat(exerciseEvent.getRegistrationPolicy()).isEqualTo(registrationPolicy);
        assertThat(exerciseEvent.getLocation()).isEqualTo(location);
        assertThat(exerciseEvent.getCategory()).isEqualTo(category);
    }

    private static Stream<Arguments> provideValidExerciseEvents() {
        return Stream.of(
                Arguments.of(
                        new User(new UserBaseInfo("xogns4909@naver.com", "그zi운아이", ".com"),
                                Role.MEMBER),
                        new EventDetail("title","Description 1", LocalDateTime.now(),
                                LocalDateTime.now().plusHours(2)),
                        new RecruitmentPolicy(3, LocalDateTime.now(),
                                LocalDateTime.now().plusHours(2)),
                        new Location(City.SEOUL, "서울시 강남구"),
                        Category.SOCCER
                )
        );
    }
}


