package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CurrentReading;
import com.mycompany.myapp.repository.CurrentReadingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.ReadingType;
/**
 * Integration tests for the {@link CurrentReadingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CurrentReadingResourceIT {

    private static final ReadingType DEFAULT_TYPE = ReadingType.TEMPERATURE;
    private static final ReadingType UPDATED_TYPE = ReadingType.CO2;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CurrentReadingRepository currentReadingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCurrentReadingMockMvc;

    private CurrentReading currentReading;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentReading createEntity(EntityManager em) {
        CurrentReading currentReading = new CurrentReading()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .timestamp(DEFAULT_TIMESTAMP);
        return currentReading;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrentReading createUpdatedEntity(EntityManager em) {
        CurrentReading currentReading = new CurrentReading()
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .timestamp(UPDATED_TIMESTAMP);
        return currentReading;
    }

    @BeforeEach
    public void initTest() {
        currentReading = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrentReading() throws Exception {
        int databaseSizeBeforeCreate = currentReadingRepository.findAll().size();
        // Create the CurrentReading
        restCurrentReadingMockMvc.perform(post("/api/current-readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currentReading)))
            .andExpect(status().isCreated());

        // Validate the CurrentReading in the database
        List<CurrentReading> currentReadingList = currentReadingRepository.findAll();
        assertThat(currentReadingList).hasSize(databaseSizeBeforeCreate + 1);
        CurrentReading testCurrentReading = currentReadingList.get(currentReadingList.size() - 1);
        assertThat(testCurrentReading.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCurrentReading.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCurrentReading.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createCurrentReadingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currentReadingRepository.findAll().size();

        // Create the CurrentReading with an existing ID
        currentReading.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentReadingMockMvc.perform(post("/api/current-readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currentReading)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentReading in the database
        List<CurrentReading> currentReadingList = currentReadingRepository.findAll();
        assertThat(currentReadingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCurrentReadings() throws Exception {
        // Initialize the database
        currentReadingRepository.saveAndFlush(currentReading);

        // Get all the currentReadingList
        restCurrentReadingMockMvc.perform(get("/api/current-readings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentReading.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }
    
    @Test
    @Transactional
    public void getCurrentReading() throws Exception {
        // Initialize the database
        currentReadingRepository.saveAndFlush(currentReading);

        // Get the currentReading
        restCurrentReadingMockMvc.perform(get("/api/current-readings/{id}", currentReading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(currentReading.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }
    @Test
    @Transactional
    public void getNonExistingCurrentReading() throws Exception {
        // Get the currentReading
        restCurrentReadingMockMvc.perform(get("/api/current-readings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrentReading() throws Exception {
        // Initialize the database
        currentReadingRepository.saveAndFlush(currentReading);

        int databaseSizeBeforeUpdate = currentReadingRepository.findAll().size();

        // Update the currentReading
        CurrentReading updatedCurrentReading = currentReadingRepository.findById(currentReading.getId()).get();
        // Disconnect from session so that the updates on updatedCurrentReading are not directly saved in db
        em.detach(updatedCurrentReading);
        updatedCurrentReading
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .timestamp(UPDATED_TIMESTAMP);

        restCurrentReadingMockMvc.perform(put("/api/current-readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrentReading)))
            .andExpect(status().isOk());

        // Validate the CurrentReading in the database
        List<CurrentReading> currentReadingList = currentReadingRepository.findAll();
        assertThat(currentReadingList).hasSize(databaseSizeBeforeUpdate);
        CurrentReading testCurrentReading = currentReadingList.get(currentReadingList.size() - 1);
        assertThat(testCurrentReading.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCurrentReading.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCurrentReading.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrentReading() throws Exception {
        int databaseSizeBeforeUpdate = currentReadingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentReadingMockMvc.perform(put("/api/current-readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(currentReading)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentReading in the database
        List<CurrentReading> currentReadingList = currentReadingRepository.findAll();
        assertThat(currentReadingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrentReading() throws Exception {
        // Initialize the database
        currentReadingRepository.saveAndFlush(currentReading);

        int databaseSizeBeforeDelete = currentReadingRepository.findAll().size();

        // Delete the currentReading
        restCurrentReadingMockMvc.perform(delete("/api/current-readings/{id}", currentReading.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrentReading> currentReadingList = currentReadingRepository.findAll();
        assertThat(currentReadingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
