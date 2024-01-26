package com.example.fitconnect.repository.review;

import com.example.fitconnect.domain.review.QReview;
import com.example.fitconnect.domain.review.Review;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class ReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory queryFactory;
    private final QReview qReview = QReview.review;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Review> findReviews(int page, int size,Long exerciseEventId, String sortBy) {
        JPAQuery<Review> query = queryFactory
                .selectFrom(qReview)
                .where(qReview.exerciseEvent.id.eq(exerciseEventId))
                .offset((page - 1) * size)
                .limit(size);

        if ("rating".equals(sortBy)) {
            query.orderBy(qReview.rating.asc());
        }
        query.orderBy(qReview.id.asc());

        List<Review> content = query.fetch();
        long total = query.fetchCount();
        return new PageImpl<>(content, PageRequest.of(page - 1, size), total);
    }
}
