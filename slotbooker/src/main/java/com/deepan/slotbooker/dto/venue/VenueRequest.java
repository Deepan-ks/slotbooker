package com.deepan.slotbooker.dto.venue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueRequest {

    @NotBlank(message = "Venue name is required")
    @Size(max = 100, message = "Venue name mustn't exceed 100 characters")
    private String venueName;

    @NotBlank(message = "Venue location is required")
    private String location;

    @NotNull(message = "Owner is required")
    private Long ownerId;

    @Size(max = 150, message = "Description mustn't exceed more than 100")
    private String description;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    @NotNull(message = "Contact number is required")
    private String contactNumber;
}
