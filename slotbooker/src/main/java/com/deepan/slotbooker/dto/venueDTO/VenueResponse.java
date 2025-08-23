package com.deepan.slotbooker.dto.venueDTO;

import com.deepan.slotbooker.model.enums.VenueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueResponse {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String mobileNumber;
    private Long ownerId;
    private VenueType type;
}
