package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopLoginDto;
import com.example.stamp_springboot.dto.ShopSignupDto;
import com.example.stamp_springboot.mapper.ShopMapper;
import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper, MongoTemplate mongoTemplate) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.mongoTemplate = mongoTemplate;
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

    // 가게 정보 수정
    public String updateShop(ShopSignupDto shopSignupDto) {
        String businessNumber = shopSignupDto.getBusinessNumber();

        Optional<ShopModel> shop = shopRepository.findByBusinessNumber(businessNumber);
        System.out.println(businessNumber);
        if (shop.isPresent()) {
            Query query = new Query(Criteria.where("businessNumber").is(businessNumber));
            Update update = new Update().set("shop_name", shopSignupDto.getShop_name());

            mongoTemplate.updateFirst(query, update, ShopModel.class);

            log.info("수정 완료");
            return "수정되었습니다.";
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
        }
        else {
            log.error("존재하지 않는 가게");
            return "존재하지 않는 가게입니다.";
        }
    }
}
