package com.aabu.finalproject.model.dto.updateReq;

import lombok.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProductImageReq {
    @URL
    @Size(max=512) private String imgUrl;
    @Size(max=255) private String publicId;
    private Boolean primary;
    @PositiveOrZero private Integer sortOrder;
}