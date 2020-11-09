package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Reading;
import com.mycompany.myapp.repository.ReadingRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Reading}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReadingResource {

    private final Logger log = LoggerFactory.getLogger(ReadingResource.class);

    private static final String ENTITY_NAME = "reading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReadingRepository readingRepository;

    public ReadingResource(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
    }

    /**
     * {@code POST  /readings} : Create a new reading.
     *
     * @param reading the reading to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reading, or with status {@code 400 (Bad Request)} if the reading has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/readings")
    public ResponseEntity<Reading> createReading(@RequestBody Reading reading) throws URISyntaxException {
        log.debug("REST request to save Reading : {}", reading);
        if (reading.getId() != null) {
            throw new BadRequestAlertException("A new reading cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reading result = readingRepository.save(reading);
        return ResponseEntity.created(new URI("/api/readings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /readings} : Updates an existing reading.
     *
     * @param reading the reading to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reading,
     * or with status {@code 400 (Bad Request)} if the reading is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reading couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/readings")
    public ResponseEntity<Reading> updateReading(@RequestBody Reading reading) throws URISyntaxException {
        log.debug("REST request to update Reading : {}", reading);
        if (reading.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reading result = readingRepository.save(reading);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reading.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /readings} : get all the readings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of readings in body.
     */
    @GetMapping("/readings")
    public List<Reading> getAllReadings() {
        log.debug("REST request to get all Readings");
        return readingRepository.findAll();
    }

    /**
     * {@code GET  /readings/:id} : get the "id" reading.
     *
     * @param id the id of the reading to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reading, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/readings/{id}")
    public ResponseEntity<Reading> getReading(@PathVariable Long id) {
        log.debug("REST request to get Reading : {}", id);
        Optional<Reading> reading = readingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reading);
    }

    /**
     * {@code DELETE  /readings/:id} : delete the "id" reading.
     *
     * @param id the id of the reading to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/readings/{id}")
    public ResponseEntity<Void> deleteReading(@PathVariable Long id) {
        log.debug("REST request to delete Reading : {}", id);
        readingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
