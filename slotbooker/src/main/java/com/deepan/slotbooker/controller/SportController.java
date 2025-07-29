package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.sport.SportResponse;
import com.deepan.slotbooker.mapper.SportsMapper;
import com.deepan.slotbooker.model.Sport;
import com.deepan.slotbooker.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportRepository sportRepository;

    @GetMapping
    public ResponseEntity<List<SportResponse>> getAllSports(){
        List<Sport> sportList = sportRepository.findAll();
        List<SportResponse> responses = sportList.stream().map(SportsMapper::buildSportResponse).toList();
        return ResponseEntity.ok(responses);
    }
}
