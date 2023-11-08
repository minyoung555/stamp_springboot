package com.example.stamp_springboot.user;

import com.example.stamp_springboot.dto.UserSignupDto;
import com.example.stamp_springboot.mapper.UserSignupMapper;
import com.example.stamp_springboot.model.UserModel;
import com.example.stamp_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

                return "success";
            }

            return "This phone number already exists";
        } catch(Exception e){
            
            throw e;
        }

    }
}
