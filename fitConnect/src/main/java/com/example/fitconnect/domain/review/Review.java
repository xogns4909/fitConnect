package com.example.fitconnect.domain.review;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.dto.review.request.ReviewUpdateDto;
import com.example.fitconnect.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 1000)
    private String content;

    private double rating;

    @ManyToOne
    private User user;

    @ManyToOne
    private ExerciseEvent exerciseEvent;

    public void updateReview(ReviewUpdateDto reviewUpdateDto, User currentUser) {
        if (!this.user.equals(currentUser)) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }

        this.content = reviewUpdateDto.getContent();
        this.rating = reviewUpdateDto.getRating();
    }


    public Review(String content, double rating, User user, ExerciseEvent exerciseEvent) {
        validationRation(rating);
        validationContent(content);
        setUser(user);
        setExerciseEvent(exerciseEvent);
        this.content = content;
        this.rating = rating;
    }
    public Review() {

    }


    private void validationRation(double rating) {
        if (rating < 1|| rating >5) {
            throw new BusinessException(ErrorMessages.INVALID_RATING);
        }
    }

    private void validationContent(String content) {
        if (content.trim().length() == 0 || content.length() >= 100) {
            throw new BusinessException(ErrorMessages.INVALID_CONTENT);
        }
    }

}
