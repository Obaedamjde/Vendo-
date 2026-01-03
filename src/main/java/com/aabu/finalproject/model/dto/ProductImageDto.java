package com.aabu.finalproject.model.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class ProductImageDto {
    private Long id;
    private String imgUrl;
    private String publicId;
    private boolean primary;
    private Integer sortOrder;
}