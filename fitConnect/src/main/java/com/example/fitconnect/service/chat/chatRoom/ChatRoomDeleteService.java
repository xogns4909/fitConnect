package com.example.fitconnect.service.chat.chatRoom;

import com.example.fitconnect.domain.chat.domain.ChatMessage;
import com.example.fitconnect.dto.chatMessage.response.ChatMessageResponseDto;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageDeleteService;
import com.example.fitconnect.service.chat.chatMessage.ChatMessageFindService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomDeleteService {

    private final ChatRoomFindService chatRoomFindService;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageFindService chatMessageFindService;

    private final ChatMessageDeleteService chatMessageDeleteService;

    public void deleteChatRoom(Long userId, Long chatRoomId) {


        ChatRoom chatRoom = chatRoomFindService.findChatRoomByChatRoomId(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CHATROOM_NOT_FOUND));

        deleteMessages(chatRoomId);
        chatRoom.validateCreator(userId);
        chatRoomRepository.delete(chatRoom);

    }

    private void deleteMessages(Long chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageFindService.findChatMessagesByChatRoomId(
                chatRoomId);
        chatMessageDeleteService.deleteMessages(chatMessages);
    }


}

