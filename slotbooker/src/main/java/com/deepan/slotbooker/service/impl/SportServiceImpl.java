package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.sportDTO.SportCreateRequest;
import com.deepan.slotbooker.dto.sportDTO.SportResponse;
import com.deepan.slotbooker.dto.sportDTO.SportUpdateRequest;
import com.deepan.slotbooker.exception.ResourceNotFoundException;
import com.deepan.slotbooker.mapper.Mapper;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.service.SportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;

    @Override
    @Transactional
    public SportResponse addSport(SportCreateRequest request) {
        // Business logic: check for existing sport to prevent duplicates
        if (sportRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Sport with this name already exists");
        }

        Sport sport = Sport.builder()
                .name(request.getName())
                .build();
        Sport newSport = sportRepository.save(sport);
        return Mapper.buildSportResponse(newSport);
    }

    @Override
    public List<SportResponse> fetchAllSports() {
        List<Sport> sports = sportRepository.findAll();
        return Mapper.buildSportResponseList(sports);
    }

    @Override
    @Transactional
    public SportResponse updateSport(Long sportId, SportUpdateRequest request) {
        Sport existingSport = sportRepository.findById(sportId)
                .orElseThrow(() -> new ResourceNotFoundException("Sport not found"));

        if (request.getName() != null) {
            existingSport.setName(request.getName());
        }

        Sport updatedSport = sportRepository.save(existingSport);
        return Mapper.buildSportResponse(updatedSport);
    }

    @Override
    @Transactional
    public Boolean deleteSport(Long sportId) {
        if (sportRepository.existsById(sportId)) {
            sportRepository.deleteById(sportId);
            return true;
        }
        return false;
    }
}
