package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.UserLoginDto;
import com.example.stamp_springboot.dto.UserSignupDto;
import com.example.stamp_springboot.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="User", description = "유저 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(
            operationId = "사용자 정보 조회",
            summary = "사용자 정보를 조회합니다.",
            description = "전화번호를 통해 사용자의 이름, 전화번호, 스탬프 리스트, 쿠폰 리스트, 생성날짜, 변경날짜를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공")
            }
    )
    @GetMapping()
    public UserModel getUser(@RequestParam(name="phoneNumber") String phoneNumber) throws Exception {
        return userService.getUser(phoneNumber);
    }

    @PostMapping("/signup")
    public String Signup(
            @RequestBody
            @Valid
            UserSignupDto signupDto
    ) {
        return this.userService.signup(signupDto);
    }

    @PostMapping("/login")
    public String Login(
            @RequestBody
            @Valid
            UserLoginDto userLoginDto
    ) {
        return this.userService.login(userLoginDto);
    }

    @DeleteMapping()
    public String deleteUser(@RequestParam(name="phoneNumber") String phoneNumber) throws Exception {
        return userService.deleteUser(phoneNumber);
    }
}
