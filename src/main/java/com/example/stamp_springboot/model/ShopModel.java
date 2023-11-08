package com.example.stamp_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection="shop")
public class ShopModel {
    @Id
    private String shop_id;

    private String shop_name;

    @Indexed(unique = true)
    @Field("business_number")
    private String businessNumber;

    @Field("created_at")
    @CreatedDate
    private LocalDateTime createTime = LocalDateTime.now();

    @Field("updated_at")
    @LastModifiedDate
    private LocalDateTime updateTime = LocalDateTime.now();
}
