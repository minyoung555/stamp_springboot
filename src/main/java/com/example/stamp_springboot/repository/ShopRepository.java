package com.example.stamp_springboot.repository;

import com.example.stamp_springboot.model.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShopRepository extends MongoRepository<ShopModel, String> {
    Optional<ShopModel> findByBusinessNumber(String business_number);
}
