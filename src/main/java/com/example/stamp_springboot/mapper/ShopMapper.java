package com.example.stamp_springboot.mapper;

import com.example.stamp_springboot.model.ShopModel;
import com.example.stamp_springboot.dto.SignupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopModel signDtoToShopModel(SignupDto signupDto);
}
