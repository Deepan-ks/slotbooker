package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.sport.SportRequest;
import com.deepan.slotbooker.dto.sport.SportResponse;
import com.deepan.slotbooker.model.Sport;

public class SportsMapper {

    private SportsMapper() {}

    /**
     * Builds a Sport entity from a create request.
     */
    public static Sport createSportEntity(SportRequest request){
        return Sport.builder()
                .sportName(request.getSportName())
                .build();
    }

    /**
     * Converts a Sport entity into response DTO.
     */
    public static SportResponse buildSportResponse(Sport sport) {
        return SportResponse.builder()
                .sportId(sport.getSportId())
                .sportName(sport.getSportName())
                .build();
    }
}
