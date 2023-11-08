package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopLoginDto;
import com.example.stamp_springboot.dto.ShopSignupDto;
import com.example.stamp_springboot.mapper.ShopMapper;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
    public void signup(ShopSignupDto shopSignupDto) {
        String business_number = shopSignupDto.getBusinessNumber();

        Optional<ShopModel> existingShop = shopRepository.findByBusinessNumber(business_number);

        if (existingShop.isPresent()) {
            log.error("이미 존재하는 가게");
        }
        else {
            ShopModel shopModel = shopMapper.signDtoToShopModel(shopSignupDto);
            shopRepository.save(shopModel);
            log.info("가게 등록 성공");
        }
    }

    // 가게 로그인
    public void login(ShopLoginDto shopLoginDto) {
        String business_number = shopLoginDto.getBusinessNumber();

        Optional<ShopModel> existingShop = shopRepository.findByBusinessNumber(business_number);

        if (existingShop.isPresent()) {
            log.info("로그인");
        }
        else {
            log.error("존재하지 않는 가게");
        }
    }

    // 가게 조회
    public Optional<ShopModel> getShop(String businessNumber) {
        Optional<ShopModel> existingshop = shopRepository.findByBusinessNumber(businessNumber);

        if (existingshop.isPresent()) {
            return existingshop;
        }
        else {
            log.info("존재하지 않는 가게");
            return Optional.empty();
        }
    }
}
