package com.example.fitconnect.domain.chat.domain;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.domain.global.BaseEntity;
import com.example.fitconnect.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;


@Entity
@Getter
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User sender;

    public ChatMessage() {
    }

    public ChatMessage(String content, ChatRoom chatRoom, User sender) {
        this.content = content;
        setChatRoom(chatRoom);
        setSender(sender);
    }

    public void setChatRoom(ChatRoom chatRoom) {
        if (this.chatRoom != null) {
            this.chatRoom.getMessages().remove(this);
        }

        this.chatRoom = chatRoom;
        chatRoom.getMessages().add(this);
    }

    public void setSender(User sender) {
        if (this.sender != null) {
            this.sender.getMessages().remove(this);
        }

        this.sender = sender;
        sender.getMessages().add(this);
    }

    public void update(String content,Long userId) {
        validateUpdateOrDelete(userId);
        this.content = content;
    }

    public void validateUpdateOrDelete(Long userId) {
        if(!this.sender.getId().equals(userId)){
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }
        if(super.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())){
            throw new BusinessException(ErrorMessages.INVALID_MESSAGE_TIME);
        }
    }
}