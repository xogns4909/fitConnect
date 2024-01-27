package com.example.fitconnect.repository.registration;

import com.example.fitconnect.domain.registration.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRegistrationRepository {

    Page<Registration> findRegistrationsByUserId(Long userId, Pageable pageable);

}
