package com.deepan.slotbooker.dto.slotDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotUpdateRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isBooked;
}
