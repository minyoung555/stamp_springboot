package com.example.stamp_springboot.dto;

import com.example.stamp_springboot.model.ShopModel;
import lombok.Data;

@Data
public class StampDto {
    private Integer count;
    private ShopModel shopId;
    private byte[] image;
}
