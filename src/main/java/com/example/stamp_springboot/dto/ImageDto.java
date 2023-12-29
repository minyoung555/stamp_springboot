package com.example.stamp_springboot.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDto {
    private String businessNumber;
    private MultipartFile image;
}
