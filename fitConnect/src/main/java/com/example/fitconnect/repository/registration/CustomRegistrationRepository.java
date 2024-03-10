package com.example.fitconnect.repository.registration;

import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRegistrationRepository {

    Page<Registration> findRegistrationsByUserId(Long userId, Pageable pageable);

    Page<Registration> findByExerciseEventIdPageAble(Long eventId, Pageable pageable);

    Optional<Registration> findRegistrationByUserAndEvent(Long userId, Long eventId);

    long countByExerciseEventIdAndStatus(Long eventId, RegistrationStatus status);

    List<Registration> findRegistrationByEventId(Long eventId);
}
