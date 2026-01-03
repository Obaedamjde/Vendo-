package com.aabu.finalproject.model.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SellerReq {


    @NotBlank @Size(max=100) private String shopName;

    @NotBlank @Pattern(regexp="^[A-Za-z0-9._]{3,30}$") private String instagramHandle;

    @NotBlank @Size(max=255) @URL
    private String instagramUrl;

    @NotBlank private String bio;
//
//    @NotNull
//    private int confirmOrder;

}
