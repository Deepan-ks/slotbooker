package com.deepan.slotbooker.dto.venue;

import lombok.*;

@Value
@Builder
public class VenueResponse {
    Long venueId;
    String venueName;
    String location;
    Long ownerId;
    Long contactNumber;
    String ownerName;
}
