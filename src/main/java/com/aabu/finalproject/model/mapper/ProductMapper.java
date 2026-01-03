package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.ProductDto;
import com.aabu.finalproject.model.dto.SearchResultItemDto;
import com.aabu.finalproject.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper(componentModel = "spring")
@Primary
public interface ProductMapper {

    @Mapping(target = "sellerId",source = "seller.id")
    @Mapping(target = "categoryId",source = "categoryEntity.id")
    @Mapping(target = "imagesDtosSet", source = "images")
    ProductDto toDto(ProductEntity entity);


    List<ProductDto> toDtoList(List<ProductEntity> entities);


    ProductEntity toEntity(ProductDto dto);


    default SearchResultItemDto toSearchResultItem(ProductEntity p) {
        if (p == null) return null;

        return SearchResultItemDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .price(p.getPrice())
                .categoryId(
                        p.getCategoryEntity() != null
                                ? p.getCategoryEntity().getId()
                                : null
                )
                .build();
    }
}
