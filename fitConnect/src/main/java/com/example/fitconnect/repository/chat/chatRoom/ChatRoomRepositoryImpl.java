package com.example.fitconnect.repository.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.chat.domain.QChatMessage;
import com.example.fitconnect.domain.chat.domain.QChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        QChatRoom qChatRoom = qChatMessage.chatRoom;

        List<ChatRoom> chatMessages = queryFactory
                .selectFrom(qChatRoom)
                .where((qChatRoom.creator.id.eq(userId)
                        .or(qChatRoom.participant.id.eq(userId))))
                .orderBy(qChatMessage.createdAt.desc())
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
}
