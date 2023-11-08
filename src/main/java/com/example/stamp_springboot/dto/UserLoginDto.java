package com.example.stamp_springboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserLoginDto {
    @NotBlank
    private String phoneNumber;
}
