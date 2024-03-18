package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadService {
    void saveImage(MultipartFile file);
   List<Image> saveImages(List<MultipartFile> files);
}