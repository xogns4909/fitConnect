package com.example.fitconnect.service.event;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.dto.event.request.ExerciseEventUpdateDto;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.image.ImageDeletionService;
import com.example.fitconnect.service.image.ImageLocalDeletionService;
import com.example.fitconnect.service.image.ImageLocalRegistrationService;
import com.example.fitconnect.service.image.ImageUploadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ExerciseEventUpdateService {

    private final ExerciseEventRepository exerciseEventRepository;

    private final ExerciseEventFindService exerciseEventFindService;

    @Autowired
    @Qualifier("imageS3DeletionService")
    private final ImageDeletionService imageDeletionService;

    @Autowired
    @Qualifier("imageS3RegistrationService")
    private final ImageUploadService imageRegistrationService;

    @Transactional
    public ExerciseEvent updateEvent(Long eventId, ExerciseEventUpdateDto updateDto,Long userId) {
        ExerciseEvent event = exerciseEventFindService.findEventByEventId(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));

        event.update(updateDto, userId);

        return event;
    }

    @Transactional
    public void updateEventImage(Long eventId, List<MultipartFile> images) {
        ExerciseEvent event = exerciseEventFindService.findEventByEventId(eventId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.EVENT_NOT_FOUND));
        deleteImage(event);
        List<Image> newImages = imageRegistrationService.saveImages(images);

        event.updateImages(newImages);
    }

    private void deleteImage(ExerciseEvent event) {
        List<Image> existImages = event.getImages();
        imageDeletionService.deleteImageList(existImages);
    }
}
