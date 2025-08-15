package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.venue.VenueRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    /**
     * get all venues
     * @return
     */
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues(){
        List <VenueResponse> responses = venueService.fetchAllVenues();
        return ResponseEntity.ok(responses);
    }

    /**
     * Create a new venue
     * @param request
     * @return
     */
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<VenueResponse> registerVenue(@Valid @RequestBody VenueRequest request){
        VenueResponse response = venueService.createVenue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
