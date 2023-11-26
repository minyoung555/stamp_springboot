package com.example.stamp_springboot.coupon;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name="Coupon", description = "쿠폰 관련 기능")
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
