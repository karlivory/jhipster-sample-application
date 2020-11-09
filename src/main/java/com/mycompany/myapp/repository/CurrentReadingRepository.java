package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CurrentReading;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CurrentReading entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentReadingRepository extends JpaRepository<CurrentReading, Long> {
}
