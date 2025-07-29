package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.VenueDTO;
import com.deepan.slotbooker.dto.venue.VenueCreateRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;

public class VenueMapper {

    /**
     * Builds an internal VenueDTO for cross-layer communication or legacy use.
     */
    public static VenueDTO mapToVenueDTO (Venue venue){
        return VenueDTO.builder()
                .id(venue.getVenueId())
                .name(venue.getVenueName())
                .location(venue.getLocation())
                .ownerId(venue.getOwnerId().getUserId())
                .build();
    }

    /**
     * Constructs a Venue entity from VenueDTO (used internally if needed).
     */
    public static Venue mapToVenueEntity (VenueDTO venueDTO){
        Venue venue = new Venue();
        venue.setVenueName(venueDTO.getName());
        venue.setLocation(venueDTO.getLocation());
        return venue;
    }

    /**
     * Builds a Venue entity from a create request and owner.
     */
    public static Venue createVenueEntity(VenueCreateRequest request, User owner) {
        return Venue.builder()
                .venueName(request.getName())
                .location(request.getLocation())
                .ownerId(owner)
                .build();
    }

    /**
     * Prepares a safe VenueResponse for client consumption.
     */
    public static VenueResponse buildVenueResponse(Venue venue) {
        return VenueResponse.builder()
                .id(venue.getVenueId())
                .name(venue.getVenueName())
                .location(venue.getLocation())
                .ownerId(venue.getOwnerId().getUserId())
                .build();
    }
}
