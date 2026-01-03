package com.aabu.finalproject.model.dto.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageReqDTO {

    @Min(0)
    private int page=0;

    @Min(1)
    @Max(100)
    private int element=20;

    //"name,asc;id,desc"
    private String sort="id,desc";
}
