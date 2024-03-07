package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.repository.image.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageDeletionServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageDeletionService imageDeletionService;

    @Value("${image.upload-dir}")
    private String uploadDir;

    @Test
    public void deleteImage_ThrowException() {
        Long imageId = 1L;
        given(imageRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> imageDeletionService.deleteImage(imageId))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
