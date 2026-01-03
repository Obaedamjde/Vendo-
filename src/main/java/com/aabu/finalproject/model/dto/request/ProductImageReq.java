package com.aabu.finalproject.model.dto.request;

import lombok.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductImageReq {

    @NotBlank @Size(max=512) @URL
    private String imgUrl;

    @NotBlank @Size(max=255) private String publicId;

    private Boolean primary;   // افتراضي false

    @NotNull @PositiveOrZero private Integer sortOrder;
}