package com.aabu.finalproject.model.mapper;


import com.aabu.finalproject.model.dto.CartDto;
import com.aabu.finalproject.model.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "userEntity.id", target = "userId")
    CartDto toDto(CartEntity entity);

    @Mapping(source = "userId", target = "userEntity.id")
    CartEntity toEntity(CartDto dto);
}
