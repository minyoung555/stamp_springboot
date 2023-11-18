package com.example.stamp_springboot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StampAddDto {
    private String businessNumber;
    private String phoneNumber;
}
