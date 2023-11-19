package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopLoginDto;
import com.example.stamp_springboot.dto.ShopSignupDto;
import com.example.stamp_springboot.model.ShopModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // 사업자 번호로 특정 가게 조회
    @GetMapping("/getShop")
    public Optional<ShopModel> getShop(@RequestParam String businessNumber) {
        return shopService.getShop(businessNumber);
    }

    // 전체 가게 조회
    @GetMapping("/allShops")
    public List<ShopModel> allShops() {
        return shopService.allShops();
    }

    // 가게 정보 수정
    @PatchMapping("/updateShop")
    public String updateShop(@RequestBody ShopSignupDto shopSignupDto) {
        return shopService.updateShop(shopSignupDto);
    }

    // 가게 삭제
    @DeleteMapping("/deleteShop")
    public String deleteShop(@RequestParam String businessNumber) {
        return shopService.deleteShop(businessNumber);
    }
}