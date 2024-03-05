package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.dto.event.request.ExerciseEventUpdateDto;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User organizer;

    private EventDetail eventDetail;

    @Embedded
    private RecruitmentPolicy registrationPolicy;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @Builder
    public ExerciseEvent(User organizer, EventDetail eventDetail,
            RecruitmentPolicy registrationPolicy, Location location,
            Category category,List<Image> images) {
        this.organizer = organizer;
        this.eventDetail = eventDetail;
        this.registrationPolicy = registrationPolicy;
        this.location = location;
        this.category = category;
        this.images = images;
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





}