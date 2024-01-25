package com.example.fitconnect.repository.review;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.user.domain.Role;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.user.domain.UserBaseInfo;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.repository.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
        ExerciseEvent exerciseEvent = new ExerciseEvent();

        userRepository.saveAndFlush(user);
        exerciseEventRepository.saveAndFlush(exerciseEvent);
        Review review1 = new Review("Content 1", 4.5, user, exerciseEvent);
        Review review2 = new Review("Content 2", 3.7, user, exerciseEvent);
        Review review3 = new Review("Content 3", 5.0, user, exerciseEvent);

        reviewRepository.saveAll(List.of(review1, review2, review3));
    }

    @Test
    public void testFindReviewsByRating() {
        Page<Review> reviews = reviewRepository.findReviews(1, 10, 2L, "rating");

        assertThat(reviews).isNotNull();
        assertThat(reviews.getContent()).hasSize(3);
        assertThat(reviews.getContent()).extracting(Review::getRating)
                .containsExactly(3.7, 4.5, 5.0);
    }

    @Test
    public void testFindReviewsByDefault() {
        Page<Review> reviews = reviewRepository.findReviews(1, 10, 1L, "default");

        assertThat(reviews).isNotNull();
        assertThat(reviews.getContent()).hasSize(3);
        assertThat(reviews.getContent().get(0).getId()).isEqualTo(1L);
    }
}
