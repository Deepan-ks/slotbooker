package com.deepan.slotbooker.dto.venue;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueCreateRequest {

    @NotBlank(message = "Venue name is required")
    private String name;

    @NotBlank(message = "Venue location is required")
    private String location;
}
