package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ActuatorDevice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActuatorDevice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActuatorDeviceRepository extends JpaRepository<ActuatorDevice, Long> {
}
