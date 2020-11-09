package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CurrentReading;
import com.mycompany.myapp.repository.CurrentReadingRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CurrentReading}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CurrentReadingResource {

    private final Logger log = LoggerFactory.getLogger(CurrentReadingResource.class);

    private static final String ENTITY_NAME = "currentReading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrentReadingRepository currentReadingRepository;

    public CurrentReadingResource(CurrentReadingRepository currentReadingRepository) {
        this.currentReadingRepository = currentReadingRepository;
    }

    /**
     * {@code POST  /current-readings} : Create a new currentReading.
     *
     * @param currentReading the currentReading to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currentReading, or with status {@code 400 (Bad Request)} if the currentReading has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/current-readings")
    public ResponseEntity<CurrentReading> createCurrentReading(@RequestBody CurrentReading currentReading) throws URISyntaxException {
        log.debug("REST request to save CurrentReading : {}", currentReading);
        if (currentReading.getId() != null) {
            throw new BadRequestAlertException("A new currentReading cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentReading result = currentReadingRepository.save(currentReading);
        return ResponseEntity.created(new URI("/api/current-readings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /current-readings} : Updates an existing currentReading.
     *
     * @param currentReading the currentReading to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentReading,
     * or with status {@code 400 (Bad Request)} if the currentReading is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currentReading couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/current-readings")
    public ResponseEntity<CurrentReading> updateCurrentReading(@RequestBody CurrentReading currentReading) throws URISyntaxException {
        log.debug("REST request to update CurrentReading : {}", currentReading);
        if (currentReading.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrentReading result = currentReadingRepository.save(currentReading);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentReading.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /current-readings} : get all the currentReadings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentReadings in body.
     */
    @GetMapping("/current-readings")
    public List<CurrentReading> getAllCurrentReadings() {
        log.debug("REST request to get all CurrentReadings");
        return currentReadingRepository.findAll();
    }

    /**
     * {@code GET  /current-readings/:id} : get the "id" currentReading.
     *
     * @param id the id of the currentReading to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currentReading, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/current-readings/{id}")
    public ResponseEntity<CurrentReading> getCurrentReading(@PathVariable Long id) {
        log.debug("REST request to get CurrentReading : {}", id);
        Optional<CurrentReading> currentReading = currentReadingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(currentReading);
    }

    /**
     * {@code DELETE  /current-readings/:id} : delete the "id" currentReading.
     *
     * @param id the id of the currentReading to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/current-readings/{id}")
    public ResponseEntity<Void> deleteCurrentReading(@PathVariable Long id) {
        log.debug("REST request to delete CurrentReading : {}", id);
        currentReadingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
