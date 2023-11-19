package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopLoginDto;
import com.example.stamp_springboot.dto.ShopSignupDto;
import com.example.stamp_springboot.mapper.ShopMapper;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

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

        Optional<ShopModel> existingShop = shopRepository.findByBusinessNumber(business_number);

        if (existingShop.isPresent()) {
            log.error("이미 존재하는 가게");
            return "이미 존재하는 가게입니다.";
        }
        else {
            ShopModel shopModel = shopMapper.signDtoToShopModel(shopSignupDto);
            shopRepository.save(shopModel);
            log.info("가게 등록 성공");
            return "가게가 등록되었습니다.";
        }
    }

    // 가게 로그인
    public String login(ShopLoginDto shopLoginDto) {
        String business_number = shopLoginDto.getBusinessNumber();

        Optional<ShopModel> existingShop = shopRepository.findByBusinessNumber(business_number);

        if (existingShop.isPresent()) {
            log.info("로그인");
            return "로그인되었습니다.";
        }
        else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }

    // 사업자 번호로 특정 가게 조회
    public Optional<ShopModel> getShop(String businessNumber) {
        Optional<ShopModel> existingshop = shopRepository.findByBusinessNumber(businessNumber);

        if (existingshop.isPresent()) {
            return existingshop;
        }
        else {
            log.error("존재하지 않는 가게");
            return Optional.empty();
        }
    }

    // 전체 가게 조회
    public List<ShopModel> allShops() {
        return shopRepository.findAll();
    }

    // 가게 이름 수정
    public String updateShopName(String businessNumber, String newName) {
        Optional<ShopModel> existingshop = shopRepository.findByBusinessNumber(businessNumber);

        if (existingshop.isPresent()) {
            String shopName = existingshop.get().getShop_name();

            if (newName.equals(shopName)) {
                log.info("수정 전 이름, 수정 후 이름 같음");
                return "수정 전 이름과 수정 후 이름이 같습니다.";
            } else {
                ShopModel shopModel = existingshop.get();
                shopModel.setShop_name(newName);
                shopRepository.save(shopModel);
                log.info("수정 완료");
                return "수정되었습니다.";
            }
        }
        else {
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
        }
        else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }
}
