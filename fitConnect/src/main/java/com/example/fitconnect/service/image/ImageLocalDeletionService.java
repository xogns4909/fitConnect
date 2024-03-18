package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.repository.image.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("imageLocalDeletionService")
public class ImageLocalDeletionService implements ImageDeletionService {

    private final ImageRepository imageRepository;

    @Value("${image.upload-dir}")
    private String uploadDir;

    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.IMAGE_NOT_FOUND));
        deleteFileInDir(image);
        imageRepository.deleteById(imageId);
    }

    @Transactional
    public void deleteImageList(List<Image> images){
        images.forEach(this::deleteFileInDir);
        imageRepository.deleteAll(images);
    }

    private void deleteFileInDir(Image image) {
        try {
            Path filePath = Paths.get(image.getImageUrl());
            log.info("filePath : {}", filePath);
            boolean deleted = Files.deleteIfExists(filePath);
            if (!deleted) {
                throw new IOException();
            }
        } catch (IOException e) {

            throw new BusinessException(ErrorMessages.FAILED_TO_DELETE_IMAGE);
        }
    }
}
