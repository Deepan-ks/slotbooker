package com.deepan.slotbooker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilityDTO {

    private Long id;

    @NotBlank(message = "Facility name is required")
    @Size(max = 100, message = "Facility name must be less than 100 characters")
    private String name;

    @NotNull(message = "VenueId must be provided")
    private Long venueId;

    @NotNull(message = "SportId must be provided")
    private Long sportId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double pricePerHour;

}
