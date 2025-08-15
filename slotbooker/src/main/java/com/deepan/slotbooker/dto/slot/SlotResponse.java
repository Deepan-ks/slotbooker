package com.deepan.slotbooker.dto.slot;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
public class SlotResponse {
    Long slotId;
    Long facilityId;
    String facilityName;
    String sportName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean isBooked;
}
