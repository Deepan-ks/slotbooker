package com.deepan.slotbooker.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long bookingId;
    private Long slotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String facilityName;
    private String venueName;
    private String playerName;
    private String status;
}
