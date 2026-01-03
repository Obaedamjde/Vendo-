package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.SellerProfileDto;
import com.aabu.finalproject.model.entity.SellerProfileEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerProfileMapper {

    SellerProfileDto toSellerDto(SellerProfileEntity entity);

    SellerProfileEntity toSellerEntity(SellerProfileDto dto);

    List<SellerProfileDto> toDto(List<SellerProfileEntity> list);
}
