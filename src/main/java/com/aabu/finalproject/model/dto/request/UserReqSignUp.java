package com.aabu.finalproject.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserReqSignUp {

    @NotBlank
    @Size(max=150) private String fullName;

    @NotBlank @Email
    @Size(max=190) private String email;
    @NotBlank @Pattern(regexp="^07[0-9]{8}$") private String phone;
    @NotBlank @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$"
    ,  message = "Password must be 8-20 characters long, include upper and lower case letters, a number, and a special character")
    private String passwordHash;
}
