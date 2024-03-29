package com.example.fitconnect.service.image;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.repository.image.ImageRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Qualifier("imageLocalRegistrationService")
@Slf4j
public class ImageLocalRegistrationService implements ImageUploadService{

    private final ImageRepository imageRepository;

    @Value("${image.upload-dir}")
    private String uploadDir;

    private final List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png");
    private final long maxFileSize = 5 * 1024 * 1024;

    @Transactional
    public void saveImage(MultipartFile file) {
        validateExtension(file.getOriginalFilename());
        validateFileSize(file.getSize());

        String storedFileName = generateStoredFileName(file.getOriginalFilename());
        Path filePath = storeFile(file, storedFileName);

        Image image = new Image(filePath.toString(), file.getOriginalFilename());
        imageRepository.save(image);
    }

    @Transactional
    public List<Image> saveImages(List<MultipartFile> files) {
        List<Image> savedImages = new ArrayList<>();
        log.info("Local 저장 서비스 입니다");
        for (MultipartFile file : files) {
            validateExtension(file.getOriginalFilename());
            validateFileSize(file.getSize());

            String storedFileName = generateStoredFileName(file.getOriginalFilename());
            Path filePath = storeFile(file, storedFileName);

            Image image = new Image(filePath.toString(), file.getOriginalFilename());
            savedImages.add(image);
        }

        return imageRepository.saveAll(savedImages);
    }

    private void validateExtension(String filename) {
        String fileExtension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        if (!allowedExtensions.contains(fileExtension)) {
            throw new BusinessException(ErrorMessages.INVALID_FILE_EXTENSION);
        }
    }

    private void validateFileSize(long size) {
        if (size > maxFileSize) {
            throw new BusinessException(ErrorMessages.FILE_SIZE_EXCEEDED);
        }
    }

    private String generateStoredFileName(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID() + fileExtension;
    }

    private Path storeFile(MultipartFile file, String storedFileName) {
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(storedFileName);
        try {
            Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new BusinessException(ErrorMessages.FILE_UPLOAD_FAILED);
        }
        return filePath;
    }
}
