package com.example.fitconnect.repository.chat.chatRoom;

import static com.example.fitconnect.domain.chat.domain.QChatRoom.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.domain.QChatMessage;
import com.example.fitconnect.domain.chat.domain.QChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

public class ChatRoomRepositoryImpl implements CustomChatRoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ChatRoom> findByChatRoomId(Long userId, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QChatMessage qChatMessage = QChatMessage.chatMessage;
        QChatRoom qChatRoom = chatRoom;

        List<ChatRoom> chatMessages = queryFactory
                .selectFrom(qChatRoom)
                .where((qChatRoom.creator.id.eq(userId)
                        .or(qChatRoom.participant.id.eq(userId))))
                .orderBy(qChatRoom.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(qChatRoom)
                .where((qChatRoom.creator.id.eq(userId)
                        .or(qChatRoom.participant.id.eq(userId))))
                .fetchCount();

        return new PageImpl<>(chatMessages, pageable, total);
    }

    @Override
    public Optional<ChatRoom> findByUserIdAndExerciseEventId(Long userId, Long exerciseEventId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QChatRoom qChatRoom = chatRoom;

        ChatRoom chatRoom = queryFactory
                .selectFrom(qChatRoom)
                .where(qChatRoom.creator.id.eq(userId)
                        .and(qChatRoom.exerciseEvent.id.eq(exerciseEventId)))
                .fetchOne();
        return Optional.ofNullable(chatRoom);
    }

    @Override
    public List<ChatRoom> findByEventId(Long eventId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(chatRoom).
                where(chatRoom.exerciseEvent.id.eq(eventId))
                .fetch();
    }
}
