package com.deepan.slotbooker.dto.facility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityResponse {
    private Long id;
    private String name;
    private Double pricePerHour;
    private Long venueId;
    private String sportName;
}
