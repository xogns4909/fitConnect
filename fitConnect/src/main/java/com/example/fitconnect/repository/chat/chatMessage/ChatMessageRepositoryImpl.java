package com.example.fitconnect.repository.chat.chatMessage;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.QChatMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;

public class ChatMessageRepositoryImpl implements CustomChatMessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ChatMessage> findLastMessageByChatRoomId(Long chatRoomId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QChatMessage qChatMessage = QChatMessage.chatMessage;

        ChatMessage chatMessage = queryFactory.selectFrom(qChatMessage)
                .where(qChatMessage.chatRoom.id.eq(chatRoomId))
                .orderBy(qChatMessage.createdAt.desc())
                .limit(1)
                .fetchOne();
        return Optional.ofNullable(chatMessage);
    }
}
