package com.aabu.finalproject.model.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class CartItemReq {
    @NotNull private Integer productId;
    @NotNull @Positive private Integer quantity;
}
