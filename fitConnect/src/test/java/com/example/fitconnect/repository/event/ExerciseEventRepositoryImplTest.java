package com.example.fitconnect.repository.event;

import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.domain.user.domain.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ExerciseEventRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExerciseEventRepositoryImpl exerciseEventRepositoryImpl;

    @Test
    public void testFindEventsByOrganizerId() {
        User organizer = new User(
                new UserBaseInfo("organizer@example.com", "Organizer", "organizerPic.jpg"),
                Role.MEMBER);
        entityManager.persist(organizer);

        ExerciseEvent event1 = createExerciseEvent(organizer);
        entityManager.persist(event1);

        ExerciseEvent event2 = createExerciseEvent(organizer);
        entityManager.persist(event2);

        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);

        Page<ExerciseEvent> result = exerciseEventRepositoryImpl.findEventsByOrganizerId(
                organizer.getId(), pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactlyInAnyOrder(event1, event2);
    }

    private static ExerciseEvent createExerciseEvent(User user) {
        List<Image> images = new ArrayList<>();
        EventDetailDto eventDetailDto = new EventDetailDto("title","Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;
        ExerciseEvent exerciseEvent = new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category).toEntity(user,images);
        return exerciseEvent;
    }
}
