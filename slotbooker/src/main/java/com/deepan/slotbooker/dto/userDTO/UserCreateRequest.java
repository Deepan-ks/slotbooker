package com.deepan.slotbooker.dto.userDTO;

import com.deepan.slotbooker.model.enums.Roles;
import com.deepan.slotbooker.model.enums.UserStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Username cannot be blank")
    private String name;

    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "User role cannot be null")
    private Roles role;

    @NotNull(message = "User status cannot be null")
    private UserStatus userStatus;
}
