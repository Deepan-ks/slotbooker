package com.deepan.slotbooker.dto.facilityDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityCreateRequest {
    @NotBlank(message = "Facility name cannot be blank")
    private String name;

    @NotNull(message = "Price cannot be null")
    private BigDecimal pricePerHour;

    @NotNull(message = "Sport ID cannot be null")
    private Long sportId;
}
