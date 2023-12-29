package com.example.stamp_springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection="image")
public class ImageModel {
    @Id
    private String image_id;

    @Indexed(unique = true)
    @Field("business_number")
    private String businessNumber;

    private byte[] image;
}
