package com.example.fitconnect.service.event;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.user.domain.User;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.repository.event.ExerciseEventRepository;
import com.example.fitconnect.service.image.ImageLocalRegistrationService;
import com.example.fitconnect.service.image.ImageUploadService;
import com.example.fitconnect.service.user.UserFindService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseEventRegistrationService {

    private final ExerciseEventRepository exerciseEventRepository;
    private final UserFindService userFindService;

    @Autowired
    @Qualifier("imageS3RegistrationService")
    private final ImageUploadService imageRegistrationService;

    @Transactional
    public ExerciseEvent registerEvent(Long userId, ExerciseEventRegistrationDto registrationDto,
            List<MultipartFile> multipartFileList) {
        User organizer = userFindService.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
        List<Image> images = new ArrayList<>();

        if (multipartFileList != null && !multipartFileList.isEmpty()) {
            images = imageRegistrationService.saveImages(multipartFileList);
        }

        ExerciseEvent exerciseEvent = registrationDto.toEntity(organizer, images);

        return exerciseEventRepository.save(exerciseEvent);
    }
}
