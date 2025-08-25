package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.venueDTO.VenueCreateRequest;
import com.deepan.slotbooker.dto.venueDTO.VenueResponse;
import com.deepan.slotbooker.dto.venueDTO.VenueUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.model.enums.Roles;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.VenueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public VenueResponse createVenue(VenueCreateRequest request, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        // Business logic: only owners can create venues
        if (!Roles.OWNER.equals(owner.getRole())) {
            throw new IllegalArgumentException("Only owners can create a venue.");
        }

        Venue venue = Venue.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .mobileNumber(request.getMobileNumber())
                .owner(owner)
                .type(request.getType())
                .build();

        Venue savedVenue = venueRepository.save(venue);
        return Mapper.buildVenueResponse(savedVenue);
    }

    @Override
    public List<VenueResponse> fetchAllVenues() {
        List<Venue> venues = venueRepository.findAll();
        return Mapper.buildVenueResponseList(venues);
    }

    @Override
    public List<VenueResponse> fetchVenuesByOwner(Long ownerId) {
        List<Venue> venues = venueRepository.findByOwnerId(ownerId);
        return Mapper.buildVenueResponseList(venues);
    }

    @Override
    public List<VenueResponse> fetchVenuesByCity(String city) {
        List<Venue> venues = venueRepository.findByCity(city);
        return Mapper.buildVenueResponseList(venues);
    }

    @Override
    @Transactional
    public VenueResponse updateVenue(Long venueId, VenueUpdateRequest request) {
        Venue existingVenue = venueRepository.findById(venueId)
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        if (request.getName() != null) {
            existingVenue.setName(request.getName());
        }
        if (request.getCity() != null) {
            existingVenue.setCity(request.getCity());
        }
        if (request.getAddress() != null) {
            existingVenue.setAddress(request.getAddress());
        }
        if (request.getMobileNumber() != null) {
            existingVenue.setMobileNumber(request.getMobileNumber());
        }
        if (request.getType() != null) {
            existingVenue.setType(request.getType());
        }

        Venue updatedVenue = venueRepository.save(existingVenue);
        return Mapper.buildVenueResponse(updatedVenue);
    }

    @Override
    @Transactional
    public Boolean deleteVenue(Long venueId) {
        if (venueRepository.existsById(venueId)) {
            venueRepository.deleteById(venueId);
            return true;
        }
        return false;
    }
}
