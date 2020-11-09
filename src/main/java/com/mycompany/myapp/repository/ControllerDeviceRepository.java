package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ControllerDevice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ControllerDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControllerDeviceRepository extends JpaRepository<ControllerDevice, Long> {
}
