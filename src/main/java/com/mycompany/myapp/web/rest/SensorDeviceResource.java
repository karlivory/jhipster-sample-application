package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SensorDevice;
import com.mycompany.myapp.repository.SensorDeviceRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.SensorDevice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SensorDeviceResource {

    private final Logger log = LoggerFactory.getLogger(SensorDeviceResource.class);

    private static final String ENTITY_NAME = "sensorDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensorDeviceRepository sensorDeviceRepository;

    public SensorDeviceResource(SensorDeviceRepository sensorDeviceRepository) {
        this.sensorDeviceRepository = sensorDeviceRepository;
    }

    /**
     * {@code POST  /sensor-devices} : Create a new sensorDevice.
     *
     * @param sensorDevice the sensorDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sensorDevice, or with status {@code 400 (Bad Request)} if the sensorDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sensor-devices")
    public ResponseEntity<SensorDevice> createSensorDevice(@RequestBody SensorDevice sensorDevice) throws URISyntaxException {
        log.debug("REST request to save SensorDevice : {}", sensorDevice);
        if (sensorDevice.getId() != null) {
            throw new BadRequestAlertException("A new sensorDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SensorDevice result = sensorDeviceRepository.save(sensorDevice);
        return ResponseEntity.created(new URI("/api/sensor-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sensor-devices} : Updates an existing sensorDevice.
     *
     * @param sensorDevice the sensorDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensorDevice,
     * or with status {@code 400 (Bad Request)} if the sensorDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensorDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sensor-devices")
    public ResponseEntity<SensorDevice> updateSensorDevice(@RequestBody SensorDevice sensorDevice) throws URISyntaxException {
        log.debug("REST request to update SensorDevice : {}", sensorDevice);
        if (sensorDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SensorDevice result = sensorDeviceRepository.save(sensorDevice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sensorDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sensor-devices} : get all the sensorDevices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensorDevices in body.
     */
    @GetMapping("/sensor-devices")
    public List<SensorDevice> getAllSensorDevices() {
        log.debug("REST request to get all SensorDevices");
        return sensorDeviceRepository.findAll();
    }

    /**
     * {@code GET  /sensor-devices/:id} : get the "id" sensorDevice.
     *
     * @param id the id of the sensorDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensorDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sensor-devices/{id}")
    public ResponseEntity<SensorDevice> getSensorDevice(@PathVariable Long id) {
        log.debug("REST request to get SensorDevice : {}", id);
        Optional<SensorDevice> sensorDevice = sensorDeviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sensorDevice);
    }

    /**
     * {@code DELETE  /sensor-devices/:id} : delete the "id" sensorDevice.
     *
     * @param id the id of the sensorDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sensor-devices/{id}")
    public ResponseEntity<Void> deleteSensorDevice(@PathVariable Long id) {
        log.debug("REST request to delete SensorDevice : {}", id);
        sensorDeviceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
