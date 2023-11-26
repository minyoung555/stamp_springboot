package com.example.stamp_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection="stamp")
public class StampModel {
    @Id
    private String id;
    @DBRef
    private ShopModel shop_id;
    private Integer count = 1;

    public StampModel(ShopModel shop_id) {
        this.shop_id = shop_id;
    }

    public void plusCount() {
        this.count++;
    }
}
