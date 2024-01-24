package com.example.fitconnect.domain.review;

import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.config.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ReviewTest {

    @ParameterizedTest
    @CsvSource({
            "'Great event!', 5.0",
            "'Good!', 1.0",
            "'Average event', 3.0"
    })
    public void createValidReview(String content, double rating) {
        Review review = new Review(content, rating);
        Assertions.assertThat(review).isNotNull();
        Assertions.assertThat(review.getRating()).isBetween(1.0, 5.0);
        Assertions.assertThat(review.getContent()).isNotBlank();
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 5.1, -1.0})
    public void invalidRatingTest(double rating) {
        Assertions.assertThatThrownBy(() -> new Review("Good event", rating))
                .isInstanceOf(BusinessException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void invalidContentTest(String content) {
        Assertions.assertThatThrownBy(() -> new Review(content, 4.0))
                .isInstanceOf(BusinessException.class);
    }

}
