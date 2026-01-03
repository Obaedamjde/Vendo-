package com.aabu.finalproject.model.dto.updateReq;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateSellerReq {
    @Size(max=100) private String shopName;
    @Pattern(regexp="^[A-Za-z0-9._]{3,30}$") private String instagramHandle;
    @URL
    @Size(max=255) private String instagramUrl;
    private String bio;
//    @NotNull
//    private int confirmOrder;
}
