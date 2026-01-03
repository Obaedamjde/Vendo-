package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.ProductImageDto;
import com.aabu.finalproject.model.entity.ProductImageEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.Set;

@Mapper(componentModel = "spring")
@Primary
public interface ProductImageMapper {

    ProductImageEntity toEntity(ProductImageDto dto);

    ProductImageDto toDto(ProductImageEntity entity);

    Set<ProductImageDto> toDtoSet(Set<ProductImageEntity> entities);


}
