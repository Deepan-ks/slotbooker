package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.venue.VenueCreateRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.VenueMapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<VenueResponse> registerVenue(@Valid @RequestBody VenueCreateRequest request){
        VenueResponse response = venueService.createVenue(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
