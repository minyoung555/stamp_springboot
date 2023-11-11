package com.example.stamp_springboot.coupon;

import com.example.stamp_springboot.model.CouponModel;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.CouponRepository;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CouponService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(UserRepository userRepository, CouponRepository couponRepository) {
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    // 쿠폰 사용
    public String useCoupon(String phoneNumber, String couponId) {
        Optional<UserModel> user = userRepository.findByPhoneNumber(phoneNumber);
        Optional<CouponModel> coupon = couponRepository.findByCouponId(couponId);

        if (user.isPresent() && coupon.isPresent()) {
            List<CouponModel> userCoupons = user.get().getCoupons();

            if (userCoupons.removeIf(c -> c.getCouponId().equals(couponId))) {
                userRepository.save(user.get());
                couponRepository.deleteByCouponId(couponId);
                log.info("쿠폰 사용 완료");
                return "쿠폰이 사용되었습니다.";
            }
            else {
                log.error("사용자가 해당 쿠폰을 소유하고 있지 않음");
                return "사용자가 해당 쿠폰을 소유하고 있지 않습니다.";
            }
        }
        else {
            log.error("존재하지 않는 사용자 또는 쿠폰");
            return "존재하지 않는 사용자 또는 쿠폰입니다.";
        }
    }

    // 쿠폰 조회
    public void getCoupon(String phoneNumber) {
        Optional<UserModel> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {

        }
        else {
            log.error("존재하지 않는 사용자");
        }
    }
}
