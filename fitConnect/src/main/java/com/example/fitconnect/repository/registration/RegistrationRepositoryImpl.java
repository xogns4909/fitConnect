package com.example.fitconnect.repository.registration;

import com.example.fitconnect.domain.registration.QRegistration;
import com.example.fitconnect.domain.registration.Registration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class RegistrationRepositoryImpl implements CustomRegistrationRepository {

    private final JPAQueryFactory queryFactory;

    public RegistrationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Registration> findRegistrationsByUserId(Long userId, Pageable pageable) {
        QRegistration registration = QRegistration.registration;
        List<Registration> registrations = queryFactory
                .selectFrom(registration)
                .where(registration.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(registration)
                .where(registration.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(registrations, pageable, total);
    }
}
