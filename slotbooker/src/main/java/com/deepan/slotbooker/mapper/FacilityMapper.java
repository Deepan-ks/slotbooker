package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.FacilityDTO;
import com.deepan.slotbooker.dto.facility.FacilityCreateRequest;
import com.deepan.slotbooker.dto.facility.FacilityResponse;
import com.deepan.slotbooker.model.Facility;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.model.Venue;

public class FacilityMapper {

    /**
     * Builds an internal FacilityDTO for cross-layer communication or legacy use.
     */
    public static FacilityDTO mapToFacilityDTO (Facility facility){
        return FacilityDTO.builder()
                .id(facility.getFacilityId())
                .name(facility.getFacilityName())
                .venueId(facility.getVenue().getVenueId())
                .sportId(facility.getSport().getSportId())
                .pricePerHour(facility.getPricePerHour())
                .build();
    }

    /**
     * Constructs a Facility entity from FacilityDTO (used internally if needed).
     */
    public static Facility mapToFacilityEntity (FacilityDTO facilityDTO){
        Facility facility = new Facility();
        facility.setFacilityName(facilityDTO.getName());
        facility.setPricePerHour(facilityDTO.getPricePerHour());
        return facility;
    }

    /**
     * Creates a Facility entity from input and parent venue/sport.
     */
    public static Facility createFacilityEntity(FacilityCreateRequest request, Venue venue, Sport sport) {
        return Facility.builder()
                .facilityName(request.getName())
                .pricePerHour(request.getPricePerHour())
                .venue(venue)
                .sport(sport)
                .build();
    }

    /**
     * Prepares a client-safe FacilityResponse from the Facility entity.
     */
    public static FacilityResponse buildFacilityResponse(Facility facility) {
        return FacilityResponse.builder()
                .id(facility.getFacilityId())
                .name(facility.getFacilityName())
                .pricePerHour(facility.getPricePerHour())
                .venueId(facility.getVenue().getVenueId())
                .sportName(facility.getSport().getSportName())
                .build();
    }
}
