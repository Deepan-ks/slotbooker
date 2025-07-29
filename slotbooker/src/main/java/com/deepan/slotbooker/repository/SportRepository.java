package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
    boolean existsBySportName(String name);
}
