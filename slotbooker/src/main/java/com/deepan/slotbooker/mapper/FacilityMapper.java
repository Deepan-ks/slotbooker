package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.facility.FacilityRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;
import org.springframework.stereotype.Component;

import java.util.List;

public class FacilityMapper {

    private FacilityMapper() {}

    /**
     * Creates a Facility entity from input and parent venue/sport.
     */
    public static Facility createFacilityEntity(FacilityRequest request, Venue venue, Sport sport) {
        return Facility.builder()
                .facilityName(request.getFacilityName())
                .venue(venue)
                .sport(sport)
                .pricePerHour(request.getPricePerHour())
                .build();
    }

    /**
     * Prepares a client-safe FacilityResponse from the Facility entity.
     */
    public static FacilityResponse buildFacilityResponse(Facility facility) {
        return FacilityResponse.builder()
                .facilityId(facility.getFacilityId())
                .facilityName(facility.getFacilityName())
                .venueId(facility.getVenue().getVenueId())
                .sportName(facility.getSport().getSportName())
                .pricePerHour(facility.getPricePerHour())
                .build();
    }

    /**
     * Updates a Facility entity from input
     */
    public static void updateEntity(Facility facility, FacilityRequest req, Venue venue, Sport sport) {
        facility.setFacilityName(req.getFacilityName());
        facility.setPricePerHour(req.getPricePerHour());
        facility.setVenue(venue);
        facility.setSport(sport);
    }

    /**
     * Transform facilities into facilityResponse List
     * @param facilities
     * @return
     */
    public static List<FacilityResponse> facilityResponseList(List<Facility> facilities){
        return facilities.stream().map(FacilityMapper::buildFacilityResponse).toList();
    }
}
