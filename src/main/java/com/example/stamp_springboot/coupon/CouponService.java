package com.example.stamp_springboot.coupon;

import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CouponService {
    private final UserRepository userRepository;

    @Autowired
    public CouponService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 쿠폰 사용
    public void useCoupon(String phoneNumber) {
        Optional<UserModel> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {

        }
        else {
            log.error("존재하지 않는 가게");
        }
    }
}
