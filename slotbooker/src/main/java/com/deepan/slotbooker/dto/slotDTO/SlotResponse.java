package com.deepan.slotbooker.dto.slotDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long facilityId;
    private Boolean isBooked;
}
