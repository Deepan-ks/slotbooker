package com.deepan.slotbooker.dto.slot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean booked;
}
