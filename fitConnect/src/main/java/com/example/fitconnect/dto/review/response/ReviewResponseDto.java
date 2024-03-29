package com.example.fitconnect.dto.review.response;

import com.example.fitconnect.domain.review.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long reviewId;
    private String reviewContent;
    private Long eventId;
    private String eventTitle;

    private String nickName;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;

    private String nickname;
    private double rating;


    public ReviewResponseDto() {
    }


    public ReviewResponseDto(Long eventId, String eventTitle, LocalDateTime eventStartTime,
            LocalDateTime eventEndTime,String nickname, Long reviewId, String reviewContent, double rating) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.nickname = nickname;
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.rating = rating;
    }

    public static ReviewResponseDto toDto(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        if (review.getExerciseEvent() != null) {
            dto.eventId = review.getExerciseEvent().getId();
            dto.eventTitle = review.getExerciseEvent().getEventDetail().getTitle();
            dto.eventStartTime = review.getExerciseEvent().getEventDetail().getStartDate();
            dto.eventEndTime = review.getExerciseEvent().getEventDetail().getEndDate();
        }
        if (review.getUser() != null) {
            dto.nickName = review.getUser().getUserBaseInfo().getNickname();
        }
        dto.reviewId = review.getId();
        dto.reviewContent = review.getContent();
        dto.rating = review.getRating();
        return dto;
    }

}
