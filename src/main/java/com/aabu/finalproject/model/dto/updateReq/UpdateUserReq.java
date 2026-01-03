package com.aabu.finalproject.model.dto.updateReq;
import com.aabu.finalproject.model.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateUserReq {
    @Size(max=150) private String fullName;

    @Email @Size(max=190) private String email;

    @Pattern(regexp="^07[0-9]{8}$") private String phone;
    private Role role;

    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$")
    private String newPassword;
}
