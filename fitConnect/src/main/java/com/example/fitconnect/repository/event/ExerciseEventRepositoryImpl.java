package com.example.fitconnect.repository.event;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.domain.QExerciseEvent;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ExerciseEventRepositoryImpl implements CustomExerciseEventRepository {

    @PersistenceContext
    private EntityManager em;


    private final JPAQueryFactory queryFactory;

    public ExerciseEventRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private final int PAGE_SIZE = 10;

    @Override
    public Page<ExerciseEvent> findEventsByOrganizerId(Long userId, Pageable pageable) {
        QExerciseEvent exerciseEvent = QExerciseEvent.exerciseEvent;
        List<ExerciseEvent> events = queryFactory
                .selectFrom(exerciseEvent)
                .where(exerciseEvent.organizer.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(exerciseEvent)
                .where(exerciseEvent.organizer.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(events, pageable, total);
    }

    @Override
    public Page<ExerciseEvent> findEventsWithConditions(Category category, String content,
            int page) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QExerciseEvent qExerciseEvent = QExerciseEvent.exerciseEvent;

        BooleanExpression predicate = buildPredicate(qExerciseEvent, category, content);
        return executeQuery(queryFactory, qExerciseEvent, predicate, page);
    }

    private BooleanExpression buildPredicate(QExerciseEvent qExerciseEvent, Category category,
            String content) {
        BooleanExpression predicate = null;

        predicate = checkSearchCategory(qExerciseEvent, category, predicate);
        predicate = checkSearchContent(qExerciseEvent, content, predicate);

        return predicate;
    }

    private BooleanExpression checkSearchContent(QExerciseEvent qExerciseEvent, String content,
            BooleanExpression predicate) {
        if (content != null && !content.isEmpty()) {
            BooleanExpression contentPredicate = qExerciseEvent.eventDetail.description.contains(
                    content);
            predicate = (predicate == null) ? contentPredicate : predicate.and(contentPredicate);
        }
        return predicate;
    }

    private BooleanExpression checkSearchCategory(QExerciseEvent qExerciseEvent, Category category,
            BooleanExpression predicate) {
        if (category != null) {
            predicate = qExerciseEvent.category.eq(category);
        }
        return predicate;
    }
    private Page<ExerciseEvent> executeQuery(JPAQueryFactory queryFactory,
            QExerciseEvent qExerciseEvent, Predicate predicate, int page) {
        List<ExerciseEvent> events = queryFactory.selectFrom(qExerciseEvent)
                .where(predicate)
                .offset((long) page * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .fetch();

        long total = queryFactory.selectFrom(qExerciseEvent)
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(events, PageRequest.of(page, PAGE_SIZE), total);
    }

}