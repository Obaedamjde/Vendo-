package com.aabu.finalproject.model.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubCategoryDto {
    private Integer id;
    private Integer category_id;
    private String name;
}