package com.aabu.finalproject.model.dto;


import com.aabu.finalproject.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "password")
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private Set<OrderDto> orderDtoSet;
    private Set<FavoriteDto> favoriteDtoSet;
    private String fullName;
    private String email;
    private String phone;
    @JsonIgnore
    private String passwordHash;
    private Role role;
    private boolean verified;
    private boolean blocked;
}
