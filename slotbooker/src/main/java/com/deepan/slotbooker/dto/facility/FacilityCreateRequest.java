package com.deepan.slotbooker.dto.facility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityCreateRequest {

    @NotBlank(message = "Facility name is required")
    private String name;

    @NotNull(message = "Sport ID is required")
    private Long sportId;

    @NotNull(message = "Price per hour is required")
    private Double pricePerHour;
}