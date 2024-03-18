package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import java.util.List;

public interface ImageDeletionService {
    void deleteImage(Long imageId);
    void deleteImageList(List<Image> imageIds);
}
