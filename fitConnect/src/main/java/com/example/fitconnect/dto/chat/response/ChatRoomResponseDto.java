package com.example.fitconnect.dto.chat.response;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private String lastMessageContent;
    private String updatedAt;

    public ChatRoomResponseDto() {
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, ChatMessage chatMessage) {
        this.id = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.lastMessageContent = (chatMessage != null) ? chatMessage.getContent() : "메시지 없음";
        this.updatedAt = (chatMessage != null) ? chatMessage.getUpdatedAt().toString() : "시간 정보 없음";

    }

    public static ChatRoomResponseDto toDto(ChatRoom chatRoom, ChatMessage lastMessage) {
        return new ChatRoomResponseDto(chatRoom, lastMessage);
    }

}
