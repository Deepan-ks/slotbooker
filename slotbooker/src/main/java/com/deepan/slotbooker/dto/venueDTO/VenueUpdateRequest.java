package com.deepan.slotbooker.dto.venueDTO;

import com.deepan.slotbooker.model.enums.VenueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueUpdateRequest {
    private String name;
    private String city;
    private String address;
    private String mobileNumber;
    private VenueType type;
}
