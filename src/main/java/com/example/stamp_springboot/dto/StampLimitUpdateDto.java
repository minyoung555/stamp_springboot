package com.example.stamp_springboot.dto;

import lombok.Data;

@Data
public class StampLimitUpdateDto {
    private String businessNumber;
    private Integer stamp_limit;
}
