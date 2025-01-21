

package com.web.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ImageUtilsTest {
    
    @Autowired
    private ImageUtils ImageCompression;

    @Test
    public void testCompressImage() {
        // Given
        byte[] inputData = "Test data for compression.".getBytes();

        // When
        byte[] compressedData = ImageCompression.compressImage(inputData);

        // Then
        // You might want to implement additional assertions based on your specific requirements
        assertArrayEquals(inputData, ImageCompression.decompressImage(compressedData));
    }

    @Test
    public void testDecompressImage() {
        // Given
        byte[] inputData = "Test data for decompression.".getBytes();
        byte[] compressedData = ImageCompression.compressImage(inputData);

        // When
        byte[] decompressedData = ImageCompression.decompressImage(compressedData);

        // Then
        // You might want to implement additional assertions based on your specific requirements
        assertArrayEquals(inputData, decompressedData);
    }
}