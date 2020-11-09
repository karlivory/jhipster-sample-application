package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Reading;
import com.mycompany.myapp.repository.ReadingRepository;

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
 * Integration tests for the {@link ReadingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReadingResourceIT {

    private static final ReadingType DEFAULT_TYPE = ReadingType.TEMPERATURE;
    private static final ReadingType UPDATED_TYPE = ReadingType.CO2;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReadingMockMvc;

    private Reading reading;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reading createEntity(EntityManager em) {
        Reading reading = new Reading()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .timestamp(DEFAULT_TIMESTAMP);
        return reading;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reading createUpdatedEntity(EntityManager em) {
        Reading reading = new Reading()
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .timestamp(UPDATED_TIMESTAMP);
        return reading;
    }

    @BeforeEach
    public void initTest() {
        reading = createEntity(em);
    }

    @Test
    @Transactional
    public void createReading() throws Exception {
        int databaseSizeBeforeCreate = readingRepository.findAll().size();
        // Create the Reading
        restReadingMockMvc.perform(post("/api/readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reading)))
            .andExpect(status().isCreated());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeCreate + 1);
        Reading testReading = readingList.get(readingList.size() - 1);
        assertThat(testReading.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReading.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testReading.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createReadingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = readingRepository.findAll().size();

        // Create the Reading with an existing ID
        reading.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReadingMockMvc.perform(post("/api/readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reading)))
            .andExpect(status().isBadRequest());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReadings() throws Exception {
        // Initialize the database
        readingRepository.saveAndFlush(reading);

        // Get all the readingList
        restReadingMockMvc.perform(get("/api/readings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reading.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }
    
    @Test
    @Transactional
    public void getReading() throws Exception {
        // Initialize the database
        readingRepository.saveAndFlush(reading);

        // Get the reading
        restReadingMockMvc.perform(get("/api/readings/{id}", reading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reading.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }
    @Test
    @Transactional
    public void getNonExistingReading() throws Exception {
        // Get the reading
        restReadingMockMvc.perform(get("/api/readings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReading() throws Exception {
        // Initialize the database
        readingRepository.saveAndFlush(reading);

        int databaseSizeBeforeUpdate = readingRepository.findAll().size();

        // Update the reading
        Reading updatedReading = readingRepository.findById(reading.getId()).get();
        // Disconnect from session so that the updates on updatedReading are not directly saved in db
        em.detach(updatedReading);
        updatedReading
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .timestamp(UPDATED_TIMESTAMP);

        restReadingMockMvc.perform(put("/api/readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReading)))
            .andExpect(status().isOk());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeUpdate);
        Reading testReading = readingList.get(readingList.size() - 1);
        assertThat(testReading.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReading.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testReading.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingReading() throws Exception {
        int databaseSizeBeforeUpdate = readingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReadingMockMvc.perform(put("/api/readings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reading)))
            .andExpect(status().isBadRequest());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReading() throws Exception {
        // Initialize the database
        readingRepository.saveAndFlush(reading);

        int databaseSizeBeforeDelete = readingRepository.findAll().size();

        // Delete the reading
        restReadingMockMvc.perform(delete("/api/readings/{id}", reading.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
