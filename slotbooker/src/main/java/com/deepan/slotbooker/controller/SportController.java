package com.deepan.slotbooker.controller;

import com.deepan.slotbooker.dto.sport.SportResponse;
import com.deepan.slotbooker.service.SportService;
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

    private final SportService sportService;

    /**
     * get all sports
     * @return
     */
    @GetMapping
    public ResponseEntity<List<SportResponse>> getAllSports(){
        List<SportResponse> responses = sportService.fetchAllSports();
        return ResponseEntity.ok(responses);
    }
}
