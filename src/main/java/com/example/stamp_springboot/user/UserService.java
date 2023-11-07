package com.example.stamp_springboot.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public String login() {
        return "success";
    }
}
