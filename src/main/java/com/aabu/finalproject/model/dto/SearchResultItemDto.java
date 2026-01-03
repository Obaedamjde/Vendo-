package com.aabu.finalproject.model.dto;



import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SearchResultItemDto {
    private Integer id;
    private String title;
    private BigDecimal price;
    private Integer categoryId;
}
