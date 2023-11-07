package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.LoginDto;
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
    public String login(@RequestBody LoginDto loginDto) {
        return this.userService.login();
    }
}
