    package com.aabu.finalproject.model.dto;

    import com.aabu.finalproject.model.enums.OrderStatus;
    import lombok.*;

    import java.math.BigDecimal;

    import java.util.Set;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public class OrderDto {
        private Integer id;
        private Long userId;
        private Long sellerId;
        private OrderStatus status;
        private BigDecimal subTotal;
        private Set<OrderItemDto> items;
    }