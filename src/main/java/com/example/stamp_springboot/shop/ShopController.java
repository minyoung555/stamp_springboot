package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.ShopSignupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) { this.shopService = shopService; }

    @PostMapping("/signup")
    public void signup(@RequestBody ShopSignupDto shopSignupDto) { shopService.signup(shopSignupDto); }
}