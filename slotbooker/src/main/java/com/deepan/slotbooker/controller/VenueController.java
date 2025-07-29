package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.VenueDTO;
import com.deepan.slotbooker.dto.venue.VenueCreateRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.VenueMapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.repository.VenueRepository;
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

    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues(){
        List<Venue> venueList = venueRepository.findAll();
        List<VenueResponse> responses = venueList.stream().map(VenueMapper::buildVenueResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<VenueResponse> registerVenue(@Valid @RequestBody VenueCreateRequest request){
        // 🔧 TEMP ONLY: mock or fetch a user manually (until auth is built)
        User currentOwner = userRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        Venue venue = VenueMapper.createVenueEntity(request,currentOwner);
        Venue savedVenue = venueRepository.save(venue);
        VenueResponse response = VenueMapper.buildVenueResponse(savedVenue);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
