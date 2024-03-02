package com.example.fitconnect.domain.review;

import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ReviewTest {

    private User user = new User();
    private ExerciseEvent exerciseEvent = new ExerciseEvent();

    @ParameterizedTest
    @CsvSource({
            "'Great event!', 5.0",
            "'Good!', 1.0",
            "'Average event', 3.0"
    })
    public void createValidReview(String content, double rating) {
        Review review = new Review(content, rating, user, exerciseEvent);
        Assertions.assertThat(review).isNotNull();
        Assertions.assertThat(review.getRating()).isBetween(1.0, 5.0);
        Assertions.assertThat(review.getContent()).isNotBlank();
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 5.1, -1.0})
    public void invalidRatingTest(double rating) {
        Assertions.assertThatThrownBy(() -> new Review("Good event", rating, user, exerciseEvent))
                .isInstanceOf(BusinessException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void invalidContentTest(String content) {
        Assertions.assertThatThrownBy(() -> new Review(content, 4.0, user, exerciseEvent))
                .isInstanceOf(BusinessException.class);
    }

}
