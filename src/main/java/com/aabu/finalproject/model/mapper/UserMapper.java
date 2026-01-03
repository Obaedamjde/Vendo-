package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.UserDto;
import com.aabu.finalproject.model.dto.request.UserReqSignUp;
import com.aabu.finalproject.model.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserEntity entity);


    UserEntity toUserEntity (UserReqSignUp req);

    List<UserDto> toDto(List<UserEntity> list);

}
