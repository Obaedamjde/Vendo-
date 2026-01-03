package com.aabu.finalproject.model.dto;
import com.aabu.finalproject.model.enums.SellerProfileStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SellerProfileDto {
    private Long id;
    private Long userId;
    private Set<ProductDto> productDtoSet;
    private Set<OrderDto> orderDtoSet;
    private String shopName;
    private String instagramHandle;
    private String instagramUrl;
    private String bio;
    private SellerProfileStatus status;
   // private int confirmOrder;
}
