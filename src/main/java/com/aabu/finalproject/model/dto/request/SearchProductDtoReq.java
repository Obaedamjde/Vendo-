package com.aabu.finalproject.model.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SearchProductDtoReq {
    private String query;
    private Integer categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
