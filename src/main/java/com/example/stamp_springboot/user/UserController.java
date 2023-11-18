package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.UserLoginDto;
import com.example.stamp_springboot.dto.UserSignupDto;
import com.example.stamp_springboot.model.UserModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

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
