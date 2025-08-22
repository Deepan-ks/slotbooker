package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.venue.VenueRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.VenueMapper;
import com.deepan.slotbooker.model.enums.Role;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;
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
    public List<VenueResponse> fetchAllVenues() {
        List<Venue> venueList = venueRepository.findAll();
        return VenueMapper.venueResponseList(venueList);
    }

    @Override
    @Transactional
    public VenueResponse createVenue(VenueRequest request) {
        User userFound = userRepository.findById(request.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        if(Role.PLAYER.equals(userFound.getUserRole())){
            throw new IllegalStateException("User is not allowed to create venue");
        }
        Venue venue = VenueMapper.createVenueEntity(request, userFound);
        Venue savedVenue = venueRepository.save(venue);
        return VenueMapper.buildVenueResponse(savedVenue);
    }

    @Override
    public VenueResponse getVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        return VenueMapper.buildVenueResponse(venue);
    }

    @Override
    public VenueResponse updateVenue(Long venueId, VenueRequest updateRequest) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        User userExists = userRepository.findById(updateRequest.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("provided user not found"));
        if(!Role.OWNER.equals(userExists.getUserRole())){
            throw new IllegalArgumentException("Provide user is not owner, please update user role");
        }
        VenueMapper.updateVenue(venue,updateRequest,userExists);
        return VenueMapper.buildVenueResponse(venueRepository.save(venue));
    }

    @Override
    public void deleteVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        venueRepository.delete(venue);
    }

}
