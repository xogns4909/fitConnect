package com.example.fitconnect.domain.user.domain;

import static com.example.fitconnect.config.error.ErrorMessages.*;

import com.example.fitconnect.config.exception.BusinessException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.review.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserBaseInfo userBaseInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonBackReference
    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseEvent> organizedEvents = new ArrayList<>();

    @JsonBackReference("user-registration")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    @JsonBackReference("user-review")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public User(UserBaseInfo userBaseInfo, Role role) {

        validateUserBaseInfo(userBaseInfo);
        validateRole(role);

        this.userBaseInfo = userBaseInfo;
        this.role = role;
    }

    public User() {

    }

    private void validateUserBaseInfo(UserBaseInfo userBaseInfo) {
        if (userBaseInfo == null) {
            throw new BusinessException(BASE_INFO_NULL);
        }
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new BusinessException(ROLE_NULL);
        }
    }
}