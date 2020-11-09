package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ControllerDevice;
import com.mycompany.myapp.repository.ControllerDeviceRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ControllerDevice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ControllerDeviceResource {

    private final Logger log = LoggerFactory.getLogger(ControllerDeviceResource.class);

    private static final String ENTITY_NAME = "controllerDevice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControllerDeviceRepository controllerDeviceRepository;

    public ControllerDeviceResource(ControllerDeviceRepository controllerDeviceRepository) {
        this.controllerDeviceRepository = controllerDeviceRepository;
    }

    /**
     * {@code POST  /controller-devices} : Create a new controllerDevice.
     *
     * @param controllerDevice the controllerDevice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controllerDevice, or with status {@code 400 (Bad Request)} if the controllerDevice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/controller-devices")
    public ResponseEntity<ControllerDevice> createControllerDevice(@RequestBody ControllerDevice controllerDevice) throws URISyntaxException {
        log.debug("REST request to save ControllerDevice : {}", controllerDevice);
        if (controllerDevice.getId() != null) {
            throw new BadRequestAlertException("A new controllerDevice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControllerDevice result = controllerDeviceRepository.save(controllerDevice);
        return ResponseEntity.created(new URI("/api/controller-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controller-devices} : Updates an existing controllerDevice.
     *
     * @param controllerDevice the controllerDevice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controllerDevice,
     * or with status {@code 400 (Bad Request)} if the controllerDevice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controllerDevice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/controller-devices")
    public ResponseEntity<ControllerDevice> updateControllerDevice(@RequestBody ControllerDevice controllerDevice) throws URISyntaxException {
        log.debug("REST request to update ControllerDevice : {}", controllerDevice);
        if (controllerDevice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ControllerDevice result = controllerDeviceRepository.save(controllerDevice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controllerDevice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /controller-devices} : get all the controllerDevices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controllerDevices in body.
     */
    @GetMapping("/controller-devices")
    public List<ControllerDevice> getAllControllerDevices() {
        log.debug("REST request to get all ControllerDevices");
        return controllerDeviceRepository.findAll();
    }

    /**
     * {@code GET  /controller-devices/:id} : get the "id" controllerDevice.
     *
     * @param id the id of the controllerDevice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controllerDevice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/controller-devices/{id}")
    public ResponseEntity<ControllerDevice> getControllerDevice(@PathVariable Long id) {
        log.debug("REST request to get ControllerDevice : {}", id);
        Optional<ControllerDevice> controllerDevice = controllerDeviceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(controllerDevice);
    }

    /**
     * {@code DELETE  /controller-devices/:id} : delete the "id" controllerDevice.
     *
     * @param id the id of the controllerDevice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/controller-devices/{id}")
    public ResponseEntity<Void> deleteControllerDevice(@PathVariable Long id) {
        log.debug("REST request to delete ControllerDevice : {}", id);
        controllerDeviceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
