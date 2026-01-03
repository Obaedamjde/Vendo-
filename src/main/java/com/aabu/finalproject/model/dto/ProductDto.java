package com.aabu.finalproject.model.dto;

import com.aabu.finalproject.model.dto.request.ProductImageReq;
import com.aabu.finalproject.model.enums.ProductStatus;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString

public class ProductDto {
    private Long id;
    private Long sellerId;
    private Integer categoryId;
    @Builder.Default
    List<ProductImageReq> imagesDtosSet = new ArrayList<>();
    private Set<CartItemDto> cartItemDtosSet;
    private Set<OrderItemDto> orderItemDtoSet;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private ProductStatus status;
}
