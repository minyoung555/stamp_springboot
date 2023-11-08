package com.example.stamp_springboot.repository;

import com.example.stamp_springboot.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByPhoneNumber(String phoneNumber);
}
