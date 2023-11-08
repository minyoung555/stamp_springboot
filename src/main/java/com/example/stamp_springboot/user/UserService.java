package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.UserLoginDto;
import com.example.stamp_springboot.dto.UserSignupDto;
import com.example.stamp_springboot.mapper.UserSignupMapper;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSignupMapper userSignupMapper;

    public String signup(UserSignupDto userSignupDto) {
        try {
            Optional<UserModel> phoneNumberIsPresent = userRepository.findByPhoneNumber(userSignupDto.getPhoneNumber());
            if(phoneNumberIsPresent.isEmpty()){
                UserModel userModel = userSignupMapper.toUserModel(userSignupDto);
                userRepository.save(userModel);

                log.info("UserSignup is success");
                return "success";
            }

            log.error("This phone number already exists");
            return "This phone number already exists";
        } catch(Exception e){
            log.error(String.valueOf(e));
            throw e;
        }
    }

    public String login(UserLoginDto userLoginDto){
        try {
            Optional<UserModel> userModel = userRepository.findByPhoneNumber(userLoginDto.getPhoneNumber());

            if(userModel.isPresent()) {
                log.info("Login Success : " + userModel.get().getName());
                return "login success";
            }

            log.error("User not found");
            return "login failed";
        } catch(Exception e) {
            log.error(String.valueOf(e));
            throw e;
        }
    }
}
