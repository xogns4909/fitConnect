package com.example.fitconnect.dto.review.request;

import lombok.Getter;

@Getter
public class ReviewRegistrationDto {

    private String content;
    private double rating;
    private Long exerciseEventId;

    public ReviewRegistrationDto(String content, double rating, Long exerciseEventId) {
        this.content = content;
        this.rating = rating;
        this.exerciseEventId = exerciseEventId;
    }
}
