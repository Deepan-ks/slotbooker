package com.deepan.slotbooker.dto.facility;

import lombok.*;

@Value
@Builder
public class FacilityResponse {
    Long facilityId;
    String facilityName;
    Double pricePerHour;
    Long venueId;
    String venueName;
    String sportName;
}
