package com.example.fitconnect.repository.registration;

import static com.example.fitconnect.domain.registration.QRegistration.registration;

import com.example.fitconnect.domain.registration.QRegistration;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.registration.RegistrationStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
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
                .leftJoin(registration.user).fetchJoin()
                .leftJoin(registration.exerciseEvent).fetchJoin()
                .where(registration.user.id.eq(userId))
                .orderBy(registration.status.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(registration)
                .where(registration.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(registrations, pageable, total);
    }



    @Override
    public Page<Registration> findByExerciseEventIdPageAble(Long eventId, Pageable pageable) {
        QRegistration registration = QRegistration.registration;
        List<Registration> registrations = queryFactory
                .selectFrom(registration)
                .join(registration.exerciseEvent).fetchJoin()
                .where(registration.exerciseEvent.id.eq(eventId))
                .orderBy(registration.status.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(registration)
                .where(registration.exerciseEvent.id.eq(eventId))
                .fetchCount();

        return new PageImpl<>(registrations, pageable, total);
    }

    @Override
    public Optional<Registration> findRegistrationByUserAndEvent(Long userId, Long eventId) {
        QRegistration registration = QRegistration.registration;
        Registration foundRegistration = queryFactory
                .selectFrom(registration)
                .where(registration.user.id.eq(userId)
                        .and(registration.exerciseEvent.id.eq(eventId)))
                .fetchOne();
        return Optional.ofNullable(foundRegistration);
    }

    @Override
    public long countByExerciseEventIdAndStatus(Long eventId, RegistrationStatus status) {
        QRegistration registration = QRegistration.registration;
        return queryFactory
                .selectFrom(registration)
                .where(registration.exerciseEvent.id.eq(eventId)
                        .and(registration.status.eq(status)))
                .fetchCount();
    }

    @Override
    public List<Registration> findRegistrationByEventId(Long eventId) {
        return queryFactory
                .selectFrom(registration)
                .where(registration.exerciseEvent.id.eq(eventId))
                .fetch();
    }

}
