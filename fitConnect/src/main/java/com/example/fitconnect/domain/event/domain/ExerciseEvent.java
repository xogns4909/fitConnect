package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.config.error.ErrorMessages;
import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.chat.ChatRoom;
import com.example.fitconnect.domain.event.dto.ExerciseEventUpdateDto;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
@Table(name = "exercise_events")
public class ExerciseEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "exerciseEvent",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "exerciseEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User organizer;

    @JsonManagedReference
    @OneToMany(mappedBy = "exerciseEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Embedded
    private EventDetail eventDetail;

    @Embedded
    private RecruitmentPolicy registrationPolicy;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public ExerciseEvent(User organizer, EventDetail eventDetail,
            RecruitmentPolicy registrationPolicy, Location location,
            Category category) {
        setOrganizer(organizer);
        this.eventDetail = eventDetail;
        this.registrationPolicy = registrationPolicy;
        this.location = location;
        this.category = category;
    }

    public void update(ExerciseEventUpdateDto updateDto, Long userId) {
        if (!this.organizer.getId().equals(userId)) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }
        this.eventDetail = updateDto.getEventDetail().toEntity();
        this.registrationPolicy = updateDto.getRecruitmentPolicy().toEntity();
        this.location = updateDto.getLocation().toEntity();
        this.category = updateDto.getCategory();
    }

    public ExerciseEvent() {

    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
        if (!organizer.getOrganizedEvents().contains(this)) {
            organizer.getOrganizedEvents().add(this);
        }
    }



}