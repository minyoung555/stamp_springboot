package com.example.stamp_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection="coupon")
public class CouponModel {
    @Id
    @Field("coupon_id")
    private String couponId;

    @DBRef
    private ShopModel shop_id;

    public CouponModel(ShopModel shopModel) {
        this.shop_id = shopModel;
    }
}