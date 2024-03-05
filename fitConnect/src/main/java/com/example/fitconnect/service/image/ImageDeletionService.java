package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageDeletionService {

    private final ImageRepository imageRepository;

    @Value("${image.upload-dir}")
    private String uploadDir;

    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.IMAGE_NOT_FOUND));
        try {
            Path filePath = Paths.get(uploadDir + image.getImageUrl());
            boolean deleted = Files.deleteIfExists(filePath);
            if (!deleted) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorMessages.FAILED_TO_DELETE_IMAGE);
        }

        imageRepository.deleteById(imageId);
    }
}
