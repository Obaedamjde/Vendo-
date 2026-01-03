package com.aabu.finalproject.model.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SubCategoryReq {
    @NotNull private Integer category_id;
    @NotBlank @Size(max=150) private String name;
}