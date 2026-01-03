package com.aabu.finalproject.model.dto.updateReq;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartItemReq {

    @NotNull
    private Long cartItemId;

    @Positive
    private Integer quantity;

}