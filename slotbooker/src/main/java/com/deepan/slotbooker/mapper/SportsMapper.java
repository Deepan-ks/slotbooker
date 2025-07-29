package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.sport.SportResponse;
import com.deepan.slotbooker.model.Sport;

public class SportsMapper {

    /**
     * Converts a Sport entity into response DTO.
     */
    public static SportResponse buildSportResponse(Sport sport) {
        return SportResponse.builder()
                .id(sport.getSportId())
                .name(sport.getSportName())
                .build();
    }
}
