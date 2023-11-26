package com.example.stamp_springboot.dto;

import lombok.Data;

@Data
public class ShopSignupDto {
    private String shop_name;
    private String businessNumber;
    private Integer stamp_limit;
}
