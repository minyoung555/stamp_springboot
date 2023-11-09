package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopLoginDto;
import com.example.stamp_springboot.dto.ShopSignupDto;
import com.example.stamp_springboot.model.ShopModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) { this.shopService = shopService; }

    // 가게 등록
    @PostMapping("/signup")
    public String signup(@RequestBody ShopSignupDto shopSignupDto) {
        return shopService.signup(shopSignupDto);
    }

    // 가게 로그인
    @PostMapping("/login")
    public String login(@RequestBody ShopLoginDto shopLoginDto) {
        return shopService.login(shopLoginDto);
    }

    // 가게 조회
    @GetMapping("/getShop")
    public Optional<ShopModel> getShop(@RequestParam String businessNumber) {
        return shopService.getShop(businessNumber);
    }

    // 가게 이름 수정
    @PatchMapping("/updateName")
    public String updateShopName(@RequestParam String businessNumber, @RequestBody String newName) {
        return shopService.updateShopName(businessNumber, newName);
    }
}