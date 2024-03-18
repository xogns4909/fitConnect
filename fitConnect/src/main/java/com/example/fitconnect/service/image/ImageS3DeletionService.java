package com.example.fitconnect.service.image;

import com.amazonaws.services.s3.AmazonS3;
import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.global.exception.EntityNotFoundException;
import com.example.fitconnect.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
@Qualifier("imageS3DeletionService")
public class ImageS3DeletionService implements ImageDeletionService{

    private final ImageRepository imageRepository;

    @Autowired
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.IMAGE_NOT_FOUND));
        String fileKey = getFileKeyFromUrl(image.getImageUrl());
        amazonS3.deleteObject(bucket, fileKey);
        imageRepository.deleteById(imageId);
        log.info("Deleted image from S3 and database: {}", fileKey);
    }

    @Transactional
    public void deleteImageList(List<Image> images){
        images.forEach(image -> {
            String fileKey = getFileKeyFromUrl(image.getImageUrl());
            amazonS3.deleteObject(bucket, fileKey);
        });
        imageRepository.deleteAll(images);
    }

    private String getFileKeyFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();
            return path.substring(path.indexOf('/') + 1);
        } catch (Exception e) {
            throw new BusinessException(ErrorMessages.FAILED_TO_EXTRACT_FILE_KEY);
        }
    }
}
