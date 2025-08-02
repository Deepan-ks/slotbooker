package com.deepan.slotbooker.service.impl;

import com.deepan.slotbooker.dto.sport.SportResponse;
import com.deepan.slotbooker.mapper.SportsMapper;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.repository.SportRepository;
import com.deepan.slotbooker.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportRepository sportRepository;

    @Override
    public List<SportResponse> fetchAllSports() {
        List<Sport> sportList = sportRepository.findAll();
        return sportList.stream().map(SportsMapper::buildSportResponse).toList();
    }
}
