package com.deepan.slotbooker.dto.bookingDTO;

import com.deepan.slotbooker.dto.slotDTO.SlotResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private LocalDateTime bookingTime;
    private String status;
    private Long playerId;
    private SlotResponse slot;
}
