package com.aabu.finalproject.model.dto;


import lombok.*;

import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryDto {
    private Integer id;
    private String name;
    private Set<SubCategoryDto> subCategoryDtoList;
    private Set<ProductDto> productDtosList;
}
