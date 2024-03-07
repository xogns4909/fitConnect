package com.example.fitconnect.service.image;

import com.example.fitconnect.domain.image.Image;
import com.example.fitconnect.global.exception.BusinessException;
import com.example.fitconnect.repository.image.ImageRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImageRegistrationServiceTest {

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    @InjectMocks
    private ImageRegistrationService imageRegistrationService;


    @Test
    public void testSaveImageSuccess() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());
        when(imageRepository.save(any(Image.class))).thenReturn(new Image());

        imageRegistrationService.saveImage(file);

        verify(imageRepository, times(1)).save(any(Image.class));
    }

    @Test
    public void testSaveImageWithExceededSize() {
        byte[] largeContent = new byte[10 * 1024 * 1024];
        MultipartFile file = new MockMultipartFile("file", "large.jpg", "image/jpeg", largeContent);

        assertThatThrownBy(() -> imageRegistrationService.saveImage(file))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    public void testSaveImageWithInvalidExtension() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "This is a text file".getBytes());

        assertThatThrownBy(() -> imageRegistrationService.saveImage(file))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    public void saveImages_ReturnImageList() throws Exception {
        MultipartFile file1 = new MockMultipartFile("file1", "filename1.jpg", "image/jpeg", "some image".getBytes());
        MultipartFile file2 = new MockMultipartFile("file2", "filename2.png", "image/png", "some image".getBytes());
        List<MultipartFile> files = Arrays.asList(file1, file2);

        Image image1 = new Image("uploaded-images/filename1.jpg", "filename1.jpg");
        Image image2 = new Image("uploaded-images/filename2.png", "filename2.png");

        when(imageRepository.save(any(Image.class)))
                .thenReturn(image1)
                .thenReturn(image2);

        List<Image> savedImages = imageRegistrationService.saveImages(files);

        assertThat(savedImages).isNotNull();
        assertThat(savedImages.size()).isEqualTo(2);
        assertThat(savedImages.get(0).getOriginalName()).isEqualTo("filename1.jpg");
        assertThat(savedImages.get(1).getOriginalName()).isEqualTo("filename2.png");
    }
}
