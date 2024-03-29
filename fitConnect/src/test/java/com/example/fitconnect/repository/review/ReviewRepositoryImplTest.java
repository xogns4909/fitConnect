package com.example.fitconnect.repository.review;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class ReviewRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReviewRepositoryImpl reviewRepositoryImpl;

    @Test
    public void FindReviewsByUserId() {
        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"), Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = createExerciseEvent(user); // `createExerciseEvent` 구현 필요
        entityManager.persist(event);

        Review review1 = new Review("Great event!", 5.0, user, event);
        entityManager.persist(review1);

        Review review2 = new Review("Had a wonderful time", 4.5, user, event);
        entityManager.persist(review2);

        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);

        Page<Review> result = reviewRepositoryImpl.findReviewsByUserId(user.getId(), pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactlyInAnyOrder(review1, review2);
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

    @Test
    public void findByUserIdAndExerciseEventId_Exists() {
        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"), Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = createExerciseEvent(user);
        entityManager.persist(event);

        Review review = new Review("Excellent event!", 5.0, user, event);
        entityManager.persist(review);

        entityManager.flush();

        Optional<Review> foundReview = reviewRepositoryImpl.findByUserIdAndExerciseEventId(user.getId(), event.getId());

        assertThat(foundReview).isPresent();
        assertThat(foundReview.get()).isEqualTo(review);
    }

    @Test
    public void findByUserIdAndExerciseEventId_NotExist() {
        User user = new User(new UserBaseInfo("user@example.com", "User", "userPic.jpg"), Role.MEMBER);
        entityManager.persist(user);

        ExerciseEvent event = createExerciseEvent(user);
        entityManager.persist(event);

        entityManager.flush();

        Optional<Review> foundReview = reviewRepositoryImpl.findByUserIdAndExerciseEventId(user.getId(), 999L);

        assertThat(foundReview).isNotPresent();
    }
}
