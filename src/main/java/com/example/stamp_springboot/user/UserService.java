package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.UserLoginDto;
import com.example.stamp_springboot.dto.UserSignupDto;
import com.example.stamp_springboot.mapper.UserSignupMapper;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Tag(name="User", description = "사용자 관련 기능")
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSignupMapper userSignupMapper;

    public String signup(UserSignupDto userSignupDto) throws Exception {
        try {
            Optional<UserModel> phoneNumberIsPresent = userRepository.findByPhoneNumber(userSignupDto.getPhoneNumber());
            if(phoneNumberIsPresent.isEmpty()){
                UserModel userModel = userSignupMapper.toUserModel(userSignupDto);
                userModel.setImage(userSignupDto.getImage().getBytes());
                userRepository.save(userModel);

                log.info("UserSignup is success");
                return "success";
            }

            log.error("This phone number already exists");
            throw new Exception("This phone number already exists");
        } catch(Exception e){
            log.error(String.valueOf(e));
            throw e;
        }
    }

    public String login(UserLoginDto userLoginDto) throws Exception {
        try {
            Optional<UserModel> loginUserModel = userRepository.findByPhoneNumber(userLoginDto.getPhoneNumber());

            if(loginUserModel.isPresent()) {
                UserModel userModel = loginUserModel.get();
                userModel.setUpdateTime(LocalDateTime.now());

                userRepository.save(userModel);

                log.info("Login Success : " + userModel.getName());
                return "login success : " + userModel.getName();
            }

            log.error("User not found");
            throw new Exception("login failed");
        } catch(Exception e) {
            log.error(String.valueOf(e));
            throw e;
        }
    }

    public UserModel getUser(String phoneNumber) throws Exception {
        try {
            Optional<UserModel> userModel = userRepository.findByPhoneNumber(phoneNumber);
            if(userModel.isPresent()) {
                log.info("getUser : Success");
                return userModel.get();
            }
            log.error("getUser : User Not Found");
            throw new Exception("User Not Found");
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw e;
        }
    }

    public String deleteUser(String phoneNumber) throws Exception {
        try {
            Optional<UserModel> userModel = userRepository.findByPhoneNumber(phoneNumber);
            if(userModel.isPresent()) {
                log.info("deleteUser : Success");
                userRepository.delete(userModel.get());
                return "success";
            }
            log.error("deleteUser : User Not Found");
            throw new Exception("User Not Found");
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw e;
        }
    }
}
