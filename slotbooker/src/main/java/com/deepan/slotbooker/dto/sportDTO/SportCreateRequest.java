package com.deepan.slotbooker.dto.sportDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportCreateRequest {
    @NotBlank(message = "Sport name cannot be blank")
    private String name;
}
