package com.deepan.slotbooker.dto.booking;

import com.deepan.slotbooker.model.enums.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
public class BookingResponse {
    Long bookingId;
    Long slotId;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String facilityName;
    String venueName;
    Long playerId;
    String playerName;
    String sportName;
    BookingStatus status;
}
