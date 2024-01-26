package com.example.fitconnect.repository.registration;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.dto.EventDetailDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.LocationDto;
import com.example.fitconnect.domain.event.dto.RecruitmentPolicyDto;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import java.time.LocalDateTime;
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
public class RegistrationRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegistrationRepositoryImpl registrationRepositoryImpl;

    @Test
    public void testFindRegistrationsByUserId() {
        User user = new User(
                new UserBaseInfo("participant@example.com", "Participant", "participantPic.jpg"),
                Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = createExerciseEvent(user);
        entityManager.persist(event);

        Registration registration1 = new Registration(user, event);
        entityManager.persist(registration1);

        Registration registration2 = new Registration(user, event);
        entityManager.persist(registration2);

        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);

        Page<Registration> result = registrationRepositoryImpl.findRegistrationsByUserId(
                user.getId(), pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactlyInAnyOrder(registration1, registration2);
    }

    private static ExerciseEvent createExerciseEvent(User user) {
        EventDetailDto eventDetailDto = new EventDetailDto("Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;
        ExerciseEvent exerciseEvent = new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category).toEntity(user);
        return exerciseEvent;
    }
}
