package com.example.stamp_springboot.shop;

import com.example.stamp_springboot.dto.*;
import com.example.stamp_springboot.model.ShopModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name="Shop", description = "사장님 관련 기능")
@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) { this.shopService = shopService; }

    // 가게 등록
    @Operation(
            operationId = "가게 등록",
            summary = "가게를 등록합니다.",
            description = "가게명, 사업자 번호, 스탬프 최대 개수를 받아 가게를 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가게 등록 성공")
            }
    )
    @PostMapping("/signup")
    public String signup(@RequestBody ShopSignupDto shopSignupDto) {
        return shopService.signup(shopSignupDto);
    }

    // 가게 로그인
    @Operation(
            operationId = "사장님 로그인",
            summary = "사장님 계정으로 로그인합니다.",
            description = "사업자 번호를 통해 로그인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사장님 로그인 성공")
            }
    )
    @PostMapping("/login")
    public String login(@RequestBody ShopLoginDto shopLoginDto) {
        return shopService.login(shopLoginDto);
    }

    // 사업자 번호로 특정 가게 조회
    @Operation(
            operationId = "특정 가게 조회",
            summary = "특정 가게를 조회합니다.",
            description = "사업자 번호를 통해 가게명, 스탬프 최대 개수, 사업자 번호, 생성 날짜, 변경 날짜를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "특정 가게 조회 성공")
            }
    )
    @GetMapping()
    public Optional<ShopModel> getShop(@RequestParam String businessNumber) {
        return shopService.getShop(businessNumber);
    }

    // 전체 가게 조회
    @Operation(
            operationId = "모든 가게 조회",
            summary = "모든 가게를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "모든 가게 조회 성공")
            }
    )
    @GetMapping("/list")
    public List<ShopModel> allShops() {
        return shopService.allShops();
    }

    // 가게 이름 수정(최종)
    @Operation(
            operationId = "가게명 변경",
            summary = "가게명을 변경합니다.",
            description = "사업자 번호, 새로운 가게명을 받아와 가게의 이름을 변경합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가게명 변경 성공")
            }
    )
    @PatchMapping("/name")
    public String updateShopName(@RequestBody ShopNameUpdateDto shopNameUpdateDto) {
        return shopService.updateShopName(shopNameUpdateDto);
    }

    // 스탬프 최대값 수정
    @Operation(
            operationId = "스탬프 최대값 변경",
            summary = "스탬프의 최대 개수를 변경합니다.",
            description = "사업자 번호, 새로운 스탬프 최대값을 받아와 가게의 스탬프 최대 개수를 변경합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스탬프 최대 개수 변경 성공")
            }
    )
    @PatchMapping("/limit")
    public String updateStampLimit(@RequestBody StampLimitUpdateDto stampLimitUpdateDto) {
        return shopService.updateStampLimit(stampLimitUpdateDto);
    }

    // 가게 삭제
    @Operation(
            operationId = "가게 삭제",
            summary = "가게를 삭제합니다.",
            description = "사업자 번호를 통해 해당하는 가게를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "가게 삭제 성공")
            }
    )
    @DeleteMapping()
    public String deleteShop(@RequestParam String businessNumber) {
        return shopService.deleteShop(businessNumber);
    }

    @Operation(
            operationId = "가게 쿠폰 정보 수정",
            summary = "가게 쿠폰 정보를 수정합니다.",
            description = "쿠폰의 카테고리와 설명을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "수정 성공")
            }
    )
    @PutMapping("/coupon")
    public  String changeCouponInformation(@RequestBody ShopCouponInformationDto shopCouponInformationDto) {
        return shopService.changeCouponInformation(shopCouponInformationDto);
    }
}