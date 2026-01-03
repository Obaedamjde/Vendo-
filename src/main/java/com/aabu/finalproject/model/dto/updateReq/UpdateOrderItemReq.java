package com.aabu.finalproject.model.dto.updateReq;


import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOrderItemReq {

    @NotNull @Positive private Integer quantity;
    @NotNull @PositiveOrZero private BigDecimal unitPrice;
}
