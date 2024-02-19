package com.example.fitconnect.dto.review.request;

import lombok.Getter;

@Getter
public class ReviewUpdateDto {

    private String content;
    private double rating;

    // Constructors
    public ReviewUpdateDto(String content, double rating) {
        this.content = content;
        this.rating = rating;
    }
}