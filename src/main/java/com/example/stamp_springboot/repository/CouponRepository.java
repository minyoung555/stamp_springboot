package com.example.stamp_springboot.repository;

import com.example.stamp_springboot.model.CouponModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CouponRepository extends MongoRepository<CouponModel, String> {
    Optional<CouponModel> deleteByCouponId(String couponId);
    Optional<CouponModel> findByCouponId(String couponId);
}
