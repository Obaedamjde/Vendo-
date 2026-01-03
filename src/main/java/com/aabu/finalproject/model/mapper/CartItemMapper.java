package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.CartItemDto;
import com.aabu.finalproject.model.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {


    CartItemDto toDto(CartItemEntity entity);
    CartItemEntity toEntity(CartItemDto dto);
}
