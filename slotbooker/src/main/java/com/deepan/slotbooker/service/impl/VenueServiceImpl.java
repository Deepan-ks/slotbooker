package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.venue.VenueCreateRequest;
import com.deepan.slotbooker.dto.venue.VenueResponse;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.VenueMapper;
import com.deepan.slotbooker.model.Role;
import com.deepan.slotbooker.model.User;
import com.deepan.slotbooker.model.Venue;
import com.deepan.slotbooker.repository.UserRepository;
import com.deepan.slotbooker.repository.VenueRepository;
import com.deepan.slotbooker.service.VenueService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<VenueResponse> fetchAllVenues() {
        List<Venue> venueList = venueRepository.findAll();
        return venueList.stream().map(VenueMapper::buildVenueResponse).toList();
    }

    @Override
    @Transactional
    public VenueResponse createVenue(VenueCreateRequest request) {
        User userFound = userRepository.findById(request.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        if(Role.PLAYER.equals(userFound.getUserRole())){
            throw new IllegalStateException("User is not allowed to create venue");
        }
        Venue venue = VenueMapper.createVenueEntity(request, userFound);
        Venue savedVenue = venueRepository.save(venue);
        return VenueMapper.buildVenueResponse(savedVenue);
    }
}
