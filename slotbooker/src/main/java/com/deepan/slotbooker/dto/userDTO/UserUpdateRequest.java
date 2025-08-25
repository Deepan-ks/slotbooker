package com.deepan.slotbooker.dto.userDTO;

import com.deepan.slotbooker.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private LocalDate birthDate;
    private String email;
    private String mobileNumber;
    private UserStatus userStatus;
}
