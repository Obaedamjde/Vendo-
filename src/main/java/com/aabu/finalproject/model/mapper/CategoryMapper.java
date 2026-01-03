package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.CategoryDto;
import com.aabu.finalproject.model.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity entity);

    CategoryEntity toEntity(CategoryDto dto);

    List<CategoryDto>toDto(List<CategoryEntity>list);

}
