package com.example.stamp_springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserSignupDto {
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
}
