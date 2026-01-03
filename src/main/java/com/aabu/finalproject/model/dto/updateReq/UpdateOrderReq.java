package com.aabu.finalproject.model.dto.updateReq;

import com.aabu.finalproject.model.enums.OrderStatus;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderReq {


    private OrderStatus status; // أو ممكن تستخدم Enum مباشرة

    private BigDecimal subTotal;

    private String buyerNote;
}