package com.aabu.finalproject.model.dto;

import lombok.*;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> items;
    private String createdAt;
    private String updatedAt;
}