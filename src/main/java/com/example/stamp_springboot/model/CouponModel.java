package com.example.stamp_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection="coupon")
public class CouponModel {
    private String couponCode;
    @DBRef
    private ShopModel shop_id;
    private String category = "기본";
    private String description = "쿠폰 설명";
}