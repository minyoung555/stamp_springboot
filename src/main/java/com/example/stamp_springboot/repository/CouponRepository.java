package com.example.stamp_springboot.repository;

import com.example.stamp_springboot.model.CouponModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CouponRepository extends MongoRepository<CouponModel, String> {
    void deleteByCouponCode(String couponCode);
    Optional<CouponModel> findByCouponCode(String couponCode);
}
