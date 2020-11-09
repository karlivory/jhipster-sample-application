package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reading;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reading entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {
}
