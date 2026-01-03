package com.aabu.finalproject.model.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReqLogIn {

    @NotBlank @Pattern(regexp="^07[0-9]{8}$") private String phone;


    @NotBlank @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$"
            ,  message = "Password must be 8-20 characters long, include upper and lower case letters, a number, and a special character")
    private String passwordHash;
}
