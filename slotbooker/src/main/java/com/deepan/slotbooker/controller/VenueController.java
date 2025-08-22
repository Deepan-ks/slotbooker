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
@RequestMapping("/api")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    // List all venues (PLAYER or OWNER)
    @GetMapping("/venues")
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    public ResponseEntity<List<VenueResponse>> getAllVenues(){
        return ResponseEntity.ok(venueService.fetchAllVenues());
    }

    // create new venue (OWNER)
    @PostMapping("/venues")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<VenueResponse> registerVenue(@Valid @RequestBody VenueRequest request){
        return ResponseEntity.ok(venueService.createVenue(request));
    }

    // get venue by ID (PLAYER or OWNER)
    @GetMapping("/venues/{id}")
    @PreAuthorize("hasAnyRole('OWNER','PLAYER')")
    public ResponseEntity<VenueResponse> getVenue(@PathVariable Long id){
        return ResponseEntity.ok(venueService.getVenue(id));
    }

    // update venue by ID (OWNER)
    @PutMapping("/venues/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<VenueResponse> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueRequest updateRequest){
        return ResponseEntity.ok(venueService.updateVenue(id, updateRequest));
    }

    // delete venue by ID (OWNER)
    @DeleteMapping("/venues/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id){
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }
}
