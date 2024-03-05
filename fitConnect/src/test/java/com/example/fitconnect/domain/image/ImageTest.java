package com.example.fitconnect.domain.image;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageTest {

    @Test
    public void createImage_withConstructor_setsAllFieldsCorrectly() {
        String imageUrl = "http://example.com/image.jpg";
        String originalName = "image.jpg";

        Image image = new Image(imageUrl, originalName);

        assertThat(image.getImageUrl()).isEqualTo(imageUrl);
        assertThat(image.getOriginalName()).isEqualTo(originalName);
    }
}
