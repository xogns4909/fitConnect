package com.example.fitconnect.repository.review;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.dto.event.request.EventDetailDto;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.LocationDto;
import com.example.fitconnect.dto.event.request.RecruitmentPolicyDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.repository.user.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ExerciseEventRepository exerciseEventRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {

        User user = new User(new UserBaseInfo("test@naver.com", "nickname",
                "url"), Role.MEMBER);
        ExerciseEvent exerciseEvent = createEvent(user);

        userRepository.saveAndFlush(user);
        exerciseEventRepository.saveAndFlush(exerciseEvent);
        Review review1 = new Review("Content 1", 4.5, user, exerciseEvent);
        Review review2 = new Review("Content 2", 3.7, user, exerciseEvent);
        Review review3 = new Review("Content 3", 5.0, user, exerciseEvent);

        reviewRepository.saveAll(List.of(review1, review2, review3));
    }

    @Test
    @DirtiesContext
    public void testFindReviewsByRating() {
        Page<Review> reviews = reviewRepository.findReviews(1, 10, 1L, "rating");

        assertThat(reviews).isNotNull();
        assertThat(reviews.getContent()).extracting(Review::getRating)
                .contains(3.7, 4.5, 5.0);
    }

    @Test
    @DirtiesContext
    public void testFindReviewsByDefault() {
        Page<Review> reviews = reviewRepository.findReviews(1, 10, 1L, "default");

        assertThat(reviews).isNotNull();
    }

    private ExerciseEvent createEvent(User user) {
        List<Image> images = new ArrayList<>();
        return new ExerciseEventRegistrationDto(
                new EventDetailDto("title", "Description", LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2)),
                new RecruitmentPolicyDto(30, LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new LocationDto(City.SEOUL, "서울시 강남구"),
                Category.SOCCER
        ).toEntity(user,images);
    }
}
