package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.FavoriteDto;
import com.aabu.finalproject.model.entity.FavoriteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(source = "userEntity.id", target = "userId")
    @Mapping(source = "productEntity.id", target = "productId")
    FavoriteDto toDto(FavoriteEntity entity);

    @Mapping(source = "userId", target = "userEntity.id")
    @Mapping(source = "productId", target = "productEntity.id")
    FavoriteEntity toEntity(FavoriteDto dto);
}
