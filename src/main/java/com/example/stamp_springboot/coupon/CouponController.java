package com.example.stamp_springboot.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            operationId = "쿠폰 사용",
            summary = "쿠폰을 사용합니다.",
            description = "사용자 전화번호, 쿠폰 코드를 통해 사용자의 쿠폰을 사용합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 사용 성공")
            }
    )
    @DeleteMapping()
    public String useCoupon(@RequestParam String phoneNumber, @RequestParam String couponCode) throws Exception {
        return couponService.useCoupon(phoneNumber, couponCode);
    }
}
