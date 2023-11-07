package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopSignupDto;
import com.example.stamp_springboot.mapper.ShopMapper;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void signup(ShopSignupDto shopSignupDto) {
        String business_number = shopSignupDto.getBusiness_number();

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
}
