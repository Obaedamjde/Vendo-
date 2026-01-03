package com.aabu.finalproject.model.mapper;

import com.aabu.finalproject.model.dto.SubCategoryDto;
import com.aabu.finalproject.model.entity.SubCategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Mapper(componentModel = "spring")
@Primary
public interface SubCategoryMapper {

    SubCategoryDto toDto(SubCategoryEntity entity);

    SubCategoryEntity toEntity(SubCategoryDto dto);

    List<SubCategoryDto>toDto(List<SubCategoryEntity> list);
}
