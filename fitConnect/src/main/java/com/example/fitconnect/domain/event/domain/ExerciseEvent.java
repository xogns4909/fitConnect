package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.registration.Registration;
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

    @OneToMany(mappedBy = "exerciseEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User organizer;

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
        this.organizer = organizer;
        this.eventDetail = eventDetail;
        this.registrationPolicy = registrationPolicy;
        this.location = location;
        this.category = category;
    }

    public ExerciseEvent() {

    }
}