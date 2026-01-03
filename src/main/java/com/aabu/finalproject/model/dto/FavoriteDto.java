package com.aabu.finalproject.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class FavoriteDto {
    private Integer id;
    private Integer userId;
    private Integer productId;

}
