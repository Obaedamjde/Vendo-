package com.aabu.finalproject.model.dto.updateReq;

import com.aabu.finalproject.model.dto.request.ProductImageReq;
import com.aabu.finalproject.model.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProductReq {
    @NotNull private Long sellerId;
    @NotNull private Integer categoryId;
    @NotBlank @Size(max=200) private String title;
    private String description;
    @NotNull @PositiveOrZero private BigDecimal price;
    @NotNull @PositiveOrZero private Integer quantity;
    @Size(max = 3)
    @Builder.Default private List<ProductImageReq> images = new ArrayList<>();

    @Builder.Default
    private ProductStatus status = ProductStatus.SOLD_IN;
}