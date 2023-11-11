package com.example.stamp_springboot.coupon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) { this.couponService = couponService; }

    // 쿠폰 사용
    @DeleteMapping("/useCoupon")
    public String useCoupon(@RequestParam String phoneNumber, @RequestParam String couponId) {
        return couponService.useCoupon(phoneNumber, couponId);
    }
}
