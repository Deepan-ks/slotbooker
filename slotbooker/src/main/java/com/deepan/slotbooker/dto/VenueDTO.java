package com.deepan.slotbooker.dto;

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
public class VenueDTO {

    private Long id;

    @NotBlank(message = "Venue name is required")
    @Size(max = 100, message = "Venue name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Venue location is required")
    @Size(max = 200, message = "Location must be less than 200 characters")
    private String location;

    @NotNull(message = "Owner ID must be provided")
    private Long ownerId;

}
