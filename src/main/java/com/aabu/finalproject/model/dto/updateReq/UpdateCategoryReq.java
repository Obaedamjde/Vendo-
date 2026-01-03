package com.aabu.finalproject.model.dto.updateReq;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateCategoryReq {
    @Size(max=150) private String name;

}