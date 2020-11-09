package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ActuatorDevice;
import com.mycompany.myapp.repository.ActuatorDeviceRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ActuatorDevice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActuatorDeviceResource {

    private final Logger log = LoggerFactory.getLogger(ActuatorDeviceResource.class);

    private static final String ENTITY_NAME = "actuatorDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActuatorDeviceRepository actuatorDeviceRepository;

    public ActuatorDeviceResource(ActuatorDeviceRepository actuatorDeviceRepository) {
        this.actuatorDeviceRepository = actuatorDeviceRepository;
    }

    /**
     * {@code POST  /actuator-devices} : Create a new actuatorDevice.
     *
     * @param actuatorDevice the actuatorDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actuatorDevice, or with status {@code 400 (Bad Request)} if the actuatorDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actuator-devices")
    public ResponseEntity<ActuatorDevice> createActuatorDevice(@RequestBody ActuatorDevice actuatorDevice) throws URISyntaxException {
        log.debug("REST request to save ActuatorDevice : {}", actuatorDevice);
        if (actuatorDevice.getId() != null) {
            throw new BadRequestAlertException("A new actuatorDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActuatorDevice result = actuatorDeviceRepository.save(actuatorDevice);
        return ResponseEntity.created(new URI("/api/actuator-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actuator-devices} : Updates an existing actuatorDevice.
     *
     * @param actuatorDevice the actuatorDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actuatorDevice,
     * or with status {@code 400 (Bad Request)} if the actuatorDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actuatorDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actuator-devices")
    public ResponseEntity<ActuatorDevice> updateActuatorDevice(@RequestBody ActuatorDevice actuatorDevice) throws URISyntaxException {
        log.debug("REST request to update ActuatorDevice : {}", actuatorDevice);
        if (actuatorDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActuatorDevice result = actuatorDeviceRepository.save(actuatorDevice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actuatorDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /actuator-devices} : get all the actuatorDevices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actuatorDevices in body.
     */
    @GetMapping("/actuator-devices")
    public List<ActuatorDevice> getAllActuatorDevices() {
        log.debug("REST request to get all ActuatorDevices");
        return actuatorDeviceRepository.findAll();
    }

    /**
     * {@code GET  /actuator-devices/:id} : get the "id" actuatorDevice.
     *
     * @param id the id of the actuatorDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actuatorDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actuator-devices/{id}")
    public ResponseEntity<ActuatorDevice> getActuatorDevice(@PathVariable Long id) {
        log.debug("REST request to get ActuatorDevice : {}", id);
        Optional<ActuatorDevice> actuatorDevice = actuatorDeviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actuatorDevice);
    }

    /**
     * {@code DELETE  /actuator-devices/:id} : delete the "id" actuatorDevice.
     *
     * @param id the id of the actuatorDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actuator-devices/{id}")
    public ResponseEntity<Void> deleteActuatorDevice(@PathVariable Long id) {
        log.debug("REST request to delete ActuatorDevice : {}", id);
        actuatorDeviceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
