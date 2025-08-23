package com.deepan.slotbooker.dto.facilityDTO;

import com.deepan.slotbooker.dto.sportDTO.SportResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityResponse {
    private Long id;
    private String name;
    private BigDecimal pricePerHour;
    private Long venueId;
    private SportResponse sport;
}
