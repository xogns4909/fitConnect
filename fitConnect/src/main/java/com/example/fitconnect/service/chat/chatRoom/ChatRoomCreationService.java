package com.example.fitconnect.service.chat.chatRoom;


import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.dto.chat.request.ChatRoomRegistrationDto;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.user.domain.User;

import com.example.fitconnect.repository.chat.chatRoom.ChatRoomRepository;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.user.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomCreationService {

    private final UserFindService userFindService;

    private final ExerciseEventFindService exerciseEventFindService;

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoom createChatRoom(ChatRoomRegistrationDto chatRoomRegistrationDto,Long creatorId) {

        User user = findUser(creatorId);
        ExerciseEvent exercise = findExercise(chatRoomRegistrationDto.getExerciseEventId());

        checkCreateRoomAuth(creatorId, exercise);
        checkDuplication(user, exercise);

        ChatRoom chatRoom = new ChatRoom(chatRoomRegistrationDto.getTitle(), exercise, user,
                exercise.getOrganizer());
        return chatRoomRepository.save(chatRoom);
    }

    private void checkDuplication(User user, ExerciseEvent exercise) {
        chatRoomRepository.findByUserIdAndExerciseEventId(user.getId(), exercise.getId())
                .ifPresent(chatRoom -> {
                    throw new BusinessException(ErrorMessages.CHATROOM_ALREADY_EXISTS);
                });
    }

    private void checkCreateRoomAuth(Long creatorId, ExerciseEvent exercise) {
        if (exercise.getOrganizer().getId().equals(creatorId)) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_CREATE_CHATROOM);
        }
    }


    private User findUser(Long userId) {
        return userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    private ExerciseEvent findExercise(Long exerciseEventID) {
        return exerciseEventFindService.findEventByEventId(exerciseEventID)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));
    }
}
