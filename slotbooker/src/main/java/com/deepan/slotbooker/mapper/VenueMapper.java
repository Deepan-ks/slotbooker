package com.deepan.slotbooker.mapper;

import com.deepan.slotbooker.dto.venue.VenueRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;

import java.util.List;
public class VenueMapper {

    private VenueMapper() {}

    /**
     * Builds a Venue entity from a create request and owner.
     */
    public static Venue createVenueEntity(VenueRequest request, User owner) {
        return Venue.builder()
                .venueName(request.getVenueName())
                .location(request.getLocation())
                .ownerId(owner)
                .build();
    }

    /**
     * Prepares a safe VenueResponse for client consumption.
     */
    public static VenueResponse buildVenueResponse(Venue venue) {
        return VenueResponse.builder()
                .venueId(venue.getVenueId())
                .venueName(venue.getVenueName())
                .location(venue.getLocation())
                .ownerId(venue.getOwnerId().getUserId())
                .ownerName(venue.getOwnerId().getUserName())
                .build();
    }

    public static void updateVenue(Venue venue, VenueRequest request, User owner){
        venue.setVenueName(request.getVenueName());
        venue.setDescription(request.getDescription());
        venue.setLocation(request.getLocation());
        venue.setContactInfo(request.getContactNumber());
        venue.setOwnerId(owner);
    }

    public static List<VenueResponse> venueResponseList(List<Venue> venueList){
        return venueList.stream().map(VenueMapper::buildVenueResponse).toList();
    }

}
