package com.deepan.slotbooker.service;

import com.deepan.slotbooker.dto.sport.SportResponse;

import java.util.List;

public interface SportService {

    List<SportResponse> fetchAllSports();
}
