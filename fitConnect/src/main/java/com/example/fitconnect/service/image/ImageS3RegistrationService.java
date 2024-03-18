package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
@Qualifier("imageS3RegistrationService")
public class ImageS3RegistrationService implements ImageUploadService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png");
    private final long maxFileSize = 5 * 1024 * 1024; // 5MB

    @Override
    @Transactional
    public void saveImage(MultipartFile file) {
        Image image = uploadFileAndCreateImage(file);
        imageRepository.save(image);
    }

    @Override
    @Transactional
    public List<Image> saveImages(List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        log.info("s3 저장 서비스 입니다");
        for (MultipartFile file : files) {
            images.add(uploadFileAndCreateImage(file));
        }
        return imageRepository.saveAll(images);
    }

    private Image uploadFileAndCreateImage(MultipartFile file) {
        validateExtension(file.getOriginalFilename());
        validateFileSize(file.getSize());

        String storedFileName = generateStoredFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, storedFileName, inputStream, metadata));
            String fileUrl = amazonS3.getUrl(bucket, storedFileName).toString();
            return new Image(fileUrl, file.getOriginalFilename());
        } catch (IOException e) {
            throw new BusinessException(ErrorMessages.FILE_UPLOAD_FAILED);
        }
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
}
