package com.deepan.slotbooker.dto.venueDTO;

import com.deepan.slotbooker.model.enums.VenueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueCreateRequest {
    @NotBlank(message = "Venue name cannot be blank")
    private String name;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull(message = "Venue type cannot be null")
    private VenueType type;
}
