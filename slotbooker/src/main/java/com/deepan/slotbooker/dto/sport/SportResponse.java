package com.deepan.slotbooker.dto.sport;

import lombok.*;

@Value
@Builder
public class SportResponse {
    Long sportId;
    String sportName;
}