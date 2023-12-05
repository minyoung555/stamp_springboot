package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.*;
import com.example.stamp_springboot.mapper.ShopMapper;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    // 가게 등록
    public String signup(ShopSignupDto shopSignupDto) {
        String business_number = shopSignupDto.getBusinessNumber();
        Integer stamp_limit = shopSignupDto.getStamp_limit();

        List<Integer> validValues = Arrays.asList(10, 15, 20);

        Optional<ShopModel> shop = shopRepository.findByBusinessNumber(business_number);

        if (shop.isPresent()) {
            log.error("이미 존재하는 가게");
            return "이미 존재하는 가게입니다.";
        } else if (!validValues.contains(stamp_limit)) {
            log.error("스탬프 최대값 설정 잘못됨");
            return "스탬프의 최대 개수는 10, 15, 20 중에서 선택해주세요.";
        }

        ShopModel shopModel = shopMapper.signDtoToShopModel(shopSignupDto);
        shopRepository.save(shopModel);
        log.info("가게 등록 성공");
        return "가게가 등록되었습니다.";
    }

    // 가게 로그인
    public String login(ShopLoginDto shopLoginDto) {
        String business_number = shopLoginDto.getBusinessNumber();

        Optional<ShopModel> existingShop = shopRepository.findByBusinessNumber(business_number);

        if (existingShop.isPresent()) {
            log.info("로그인");
            return "로그인되었습니다.";
        } else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }

    // 사업자 번호로 특정 가게 조회
    public Optional<ShopModel> getShop(String businessNumber) {
        Optional<ShopModel> existingshop = shopRepository.findByBusinessNumber(businessNumber);

        if (existingshop.isPresent()) {
            return existingshop;
        } else {
            log.error("존재하지 않는 가게");
            return Optional.empty();
        }
    }

    // 전체 가게 조회
    public List<ShopModel> allShops() {
        return shopRepository.findAll();
    }

    // 가게 이름 수정(최종)
    public String updateShopName(ShopNameUpdateDto shopNameUpdateDto) {
        Optional<ShopModel> shop = shopRepository.findByBusinessNumber(shopNameUpdateDto.getBusinessNumber());

        if (shop.isPresent()) {
            String shopName = shop.get().getShop_name();
            String newName = shopNameUpdateDto.getShop_name();
            if (newName.equals(shopName)) {
                log.info("수정 전 이름, 수정 후 이름 같음");
                return "수정 전 이름과 수정 후 이름이 같습니다.";
            } else {
                ShopModel shopModel = shop.get();
                shopModel.setShop_name(newName);
                shopRepository.save(shopModel);
                log.info("가게 이름 수정 완료");
                return "가게 이름이 수정되었습니다.";
            }
        } else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }

    // 스탬프 최대값 수정
    public String updateStampLimit(StampLimitUpdateDto stampLimitUpdateDto) {
        Optional<ShopModel> shop = shopRepository.findByBusinessNumber(stampLimitUpdateDto.getBusinessNumber());

        List<Integer> validValues = Arrays.asList(10, 15, 20);

        if (shop.isPresent()) {
            Integer stamp_limit = shop.get().getStamp_limit();
            Integer newLimit = stampLimitUpdateDto.getStamp_limit();
            if (newLimit.equals(stamp_limit)) {
                log.info("수정 전후 같음");
                return "수정 전 개수와 수정 후 개수가 같습니다.";
            } else if (!validValues.contains(newLimit)) {
                log.error("스탬프 최대값 설정 잘못됨");
                return "스탬프의 최대 개수는 10, 15, 20 중에서 선택해주세요.";
            }

            ShopModel shopModel = shop.get();
            shopModel.setStamp_limit(newLimit);
            log.info("스탬프 최대값 수정 완료");
            shopRepository.save(shopModel);
            return "스탬프의 최대 개수가 수정되었습니다.";
        } else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }

    // 가게 삭제
    public String deleteShop(String businessNumber) {
        Optional<ShopModel> shop = shopRepository.findByBusinessNumber(businessNumber);

        if (shop.isPresent()) {
            shopRepository.deleteByBusinessNumber(businessNumber);
            log.info("삭제 완료");
            return "가게가 삭제되었습니다.";
        } else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }

    public String changeCouponInformation(ShopCouponInformationDto shopCouponInformationDto) {
        Optional<ShopModel> shop = shopRepository.findByBusinessNumber(shopCouponInformationDto.getBusinessNumber());
        if (shop.isPresent()) {
            shop.get().setCoupon_category(shopCouponInformationDto.getCoupon_category());
            shop.get().setCoupon_description(shopCouponInformationDto.getCoupon_description());
            shopRepository.save(shop.get());
            log.info("수정 완료");
            return "수정되었습니다.";
        } else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }
}
