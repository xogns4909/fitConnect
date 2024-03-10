package com.example.fitconnect.repository.registration;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
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
    @Autowired
    private RegistrationRepository registrationRepository;


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

    @Test
    public void findByExerciseEventIdTest() {
        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"),
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

        Page<Registration> result = registrationRepository.findByExerciseEventId(event.getId(),
                pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactlyInAnyOrder(registration1, registration2);
    }

    @Test
    public void testFindRegistrationByUserAndEvent() {

        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"),
                Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = createExerciseEvent(user);
        entityManager.persist(event);

        Registration registration = new Registration(user, event);
        entityManager.persist(registration);

        entityManager.flush();

        Optional<Registration> result = registrationRepository.findRegistrationByUserAndEvent(
                user.getId(), event.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(registration);
    }

    @Test
    public void testFindRegistrationByUserAndEvent_NotFound() {
        Optional<Registration> result = registrationRepository.findRegistrationByUserAndEvent(999L,
                999L);

        assertThat(result).isNotPresent();
    }

    @Test
    public void testCountByExerciseEventIdAndStatus() {
        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"),
                Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = createExerciseEvent(user);
        entityManager.persist(event);


        Registration registrationApproved = new Registration(user, event);
        registrationApproved.setStatus(RegistrationStatus.APPROVED);
        entityManager.persist(registrationApproved);

        Registration registrationPending = new Registration(user, event);
        entityManager.persist(registrationPending);

        entityManager.flush();


        long countApproved = registrationRepositoryImpl.countByExerciseEventIdAndStatus(
                event.getId(), RegistrationStatus.APPROVED);
        assertThat(countApproved).isEqualTo(1);


        long countPending = registrationRepositoryImpl.countByExerciseEventIdAndStatus(
                event.getId(), RegistrationStatus.APPLIED);
        assertThat(countPending).isEqualTo(1);
    }


    private static ExerciseEvent createExerciseEvent(User user) {
        EventDetailDto eventDetailDto = new EventDetailDto("title", "Description",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");
        Category category = Category.SOCCER;
        ExerciseEvent exerciseEvent = new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category).toEntity(user,new ArrayList<>());
        return exerciseEvent;
    }
}
