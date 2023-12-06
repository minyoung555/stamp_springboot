package com.example.stamp_springboot.coupon;

import com.example.stamp_springboot.dto.StampAddDto;
import com.example.stamp_springboot.model.CouponModel;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.CouponRepository;
import com.example.stamp_springboot.repository.ShopRepository;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CouponService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public CouponService(UserRepository userRepository, ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
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

    // 쿠폰 조회
    public List<CouponModel> getCoupon(String phoneNumber) {
        Optional<UserModel> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            List<CouponModel> coupons = user.get().getCoupons();
            return coupons;
        }

        log.error("존재하지 않는 사용자");
        return user.map(UserModel::getCoupons).orElse(null);
    }

    public String addCoupon(StampAddDto stampAddDto) throws Exception {
        Optional<UserModel> userModel = userRepository.findByPhoneNumber(stampAddDto.getPhoneNumber());
        if(userModel.isPresent()) {
            Optional<ShopModel> shopModel = shopRepository.findByBusinessNumber(stampAddDto.getBusinessNumber());
            if(shopModel.isPresent()) {
                UUID couponCode = UUID.randomUUID();
                CouponModel couponModel = new CouponModel(String.valueOf(couponCode), shopModel.get(), shopModel.get().getCoupon_category(), shopModel.get().getCoupon_description());
                List<CouponModel> couponModels = userModel.get().getCoupons();
                couponModels.add(couponModel);
                userModel.get().setCoupons(couponModels);
                userRepository.save(userModel.get());
                log.info("addCoupon : coupon create");
                return "coupon create";
            }
            log.error("addCoupon : shop not found");
            throw new Exception("shop not found");
        }
        log.error("addCoupon ; user not found");
        throw new Exception("user not found");
    }
}


