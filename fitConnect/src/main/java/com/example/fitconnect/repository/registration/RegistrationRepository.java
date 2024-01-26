package com.example.fitconnect.repository.registration;

import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.repository.review.CustomReviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>,
        CustomRegistrationRepository {

}
