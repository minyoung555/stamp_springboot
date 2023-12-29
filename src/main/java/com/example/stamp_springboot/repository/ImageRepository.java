package com.example.stamp_springboot.repository;

import com.example.stamp_springboot.model.ImageModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ImageRepository extends MongoRepository<ImageModel, String> {
    Optional<ImageModel> findImageModelByBusinessNumber(String businessNumber);
}
