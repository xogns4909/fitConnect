package com.example.fitconnect.service.event;

import com.example.fitconnect.domain.chat.domain.ChatRoom;
import com.example.fitconnect.domain.registration.Registration;
import com.example.fitconnect.domain.review.Review;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.chat.chatRoom.ChatRoomFindService;
import com.example.fitconnect.service.registration.RegistrationFindService;
import com.example.fitconnect.service.review.ReviewDeletionService;
import com.example.fitconnect.service.review.ReviewFindService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExerciseEventDeleteService {

    private final ExerciseEventRepository exerciseEventRepository;

    private final ChatRoomFindService chatRoomFindService;

    private final RegistrationFindService registrationFindService;

    private final ReviewFindService reviewFindService;

    private final ReviewDeletionService reviewDeletionService;


    @Transactional
    public void deleteEvent(Long eventId, Long userId) {
        ExerciseEvent event = exerciseEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));

        if (!event.getOrganizer().getId().equals(userId)) {
            throw new BusinessException(ErrorMessages.UNAUTHORIZED_USER);
        }

        List<ChatRoom> chatRooms = chatRoomFindService.findChatRoomsByEventId(eventId);
        chatRooms.forEach(ChatRoom::detachEvent);

        List<Registration> registrations = registrationFindService.findByEventId(eventId);
        registrations.forEach(Registration::detachEvent);

        List<Review> reviews = reviewFindService.findReviewsByEventId(eventId)
                .orElse(Collections.emptyList());
        reviewDeletionService.deleteReviews(reviews);

        exerciseEventRepository.delete(event);
    }
}