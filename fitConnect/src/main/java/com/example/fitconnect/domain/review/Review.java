package com.example.fitconnect.domain.review;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private final int MIN_RATING = 1;
    private final int MAX_RATING = 5;
    private final int MAX_CONTENT_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Review(String content, double rating) {
        validationRation(rating);
        validationContent(content);
        this.content = content;
        this.rating = rating;
    }

    @Column(length = 1000)
    private String content;

    private double rating;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "exerciseEvent_id")
    private ExerciseEvent exerciseEvent;

    public Review() {

    }


    public void validationRation(double rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new BusinessException(ErrorMessages.INVALID_RATING);
        }
    }
    public void validationContent(String content){
        if(content.trim().length() == 0 || content.length() >= MAX_CONTENT_LENGTH){
            throw new BusinessException(ErrorMessages.INVALID_CONTENT);
        }
    }



}
