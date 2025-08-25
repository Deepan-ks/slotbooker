package com.deepan.slotbooker.dto.facilityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityUpdateRequest {
    private String name;
    private BigDecimal pricePerHour;
    private Long sportId;
}
