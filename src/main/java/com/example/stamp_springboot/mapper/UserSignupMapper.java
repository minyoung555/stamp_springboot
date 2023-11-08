package com.example.stamp_springboot.mapper;

import com.example.stamp_springboot.dto.UserSignupDto;
import com.example.stamp_springboot.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserSignupMapper {
    UserSignupMapper INSTANCE = Mappers.getMapper(UserSignupMapper.class);

    @Mapping(source="name",target="name")
    @Mapping(source="phoneNumber",target="phoneNumber")
    UserModel toUserModel(UserSignupDto userSignupDto);
}
