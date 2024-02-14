package com.example.fitconnect.repository.event;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.EventDetailDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.LocationDto;
import com.example.fitconnect.domain.event.dto.RecruitmentPolicyDto;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ExerciseEventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExerciseEventRepository exerciseEventRepository;

    @ParameterizedTest
    @CsvSource({
            "SOCCER, Soccer match in Seoul, SEOUL, content",
            "BASKETBALL, Basketball game tonight, SEOUL, content",
            "FITNESS, Fitness event this weekend, SEOUL, content"
    })
    public void FindByCategory_Success(Category category, String description, City city, String searchBy
           ) {
        User user = new User(new UserBaseInfo("xogns4909@naver.com", "그zi운아이", "abc.com"),
                Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent newEvent = createEventRegistrationDto(category, description).toEntity(user);
        entityManager.persist(newEvent);
        entityManager.flush();

        Page<ExerciseEvent> foundEvents = exerciseEventRepository.findEventsWithConditions(category,
                city, searchBy, description, 0);

        assertThat(foundEvents.getContent()).containsExactly(newEvent);
    }

    private static ExerciseEventRegistrationDto createEventRegistrationDto(Category category,
            String description) {
        EventDetailDto eventDetailDto = new EventDetailDto("title", description,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
        RecruitmentPolicyDto recruitmentPolicyDto = new RecruitmentPolicyDto(30,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        LocationDto locationDto = new LocationDto(City.SEOUL, "서울시 강남구");

        return new ExerciseEventRegistrationDto(eventDetailDto,
                recruitmentPolicyDto, locationDto, category);
    }
}