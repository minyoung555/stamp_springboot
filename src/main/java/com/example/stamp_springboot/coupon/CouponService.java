package com.example.stamp_springboot.coupon;

import com.example.stamp_springboot.model.CouponModel;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.CouponRepository;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public String useCoupon(String phoneNumber, String couponCode) throws Exception {
        Optional<UserModel> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            List<CouponModel> userCoupons = user.get().getCoupons();
            Optional<CouponModel> couponModel = findCoupon(userCoupons, couponCode);

            if(couponModel.isPresent()) {
                if (userCoupons.removeIf(c -> c.getCouponCode().equals(couponCode))) {
                    user.get().setCoupons(userCoupons);
                    userRepository.save(user.get());
                    log.info("쿠폰 사용 완료");
                    return "쿠폰이 사용되었습니다.";
                }
            }
            log.error("사용자가 해당 쿠폰을 소유하고 있지 않음");
            throw new Exception("사용자가 해당 쿠폰을 소유하고 있지 않습니다.");

        }

        log.error("존재하지 않는 사용자 또는 쿠폰");
        throw new Exception("존재하지 않는 사용자 또는 쿠폰입니다.");
    }

    private Optional<CouponModel> findCoupon(List<CouponModel> couponModels, String couponCode) {
        for(CouponModel couponModel : couponModels) {
            if(Objects.equals(couponModel.getCouponCode(), couponCode)) {
                return Optional.of(couponModel);
            }
        }
        return Optional.empty();
    }
}


