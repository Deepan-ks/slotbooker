package com.deepan.slotbooker.repository;

import com.deepan.slotbooker.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Sport entity.
 */
@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
    boolean existsByName(String name);
}
