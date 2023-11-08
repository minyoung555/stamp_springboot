package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.UserLoginDto;
import com.example.stamp_springboot.dto.UserSignupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public String login(
            @RequestBody
            @Valid
            UserSignupDto signupDto
    ) {
        return this.userService.signup(signupDto);
    }
}
