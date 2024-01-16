package com.example.fitconnect.repository.user;

import com.example.fitconnect.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserBaseInfo_Email(String email);
}
