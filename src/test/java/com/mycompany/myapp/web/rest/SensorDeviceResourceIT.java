package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.SensorDevice;
import com.mycompany.myapp.repository.SensorDeviceRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SensorDeviceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SensorDeviceResourceIT {

    @Autowired
    private SensorDeviceRepository sensorDeviceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensorDeviceMockMvc;

    private SensorDevice sensorDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorDevice createEntity(EntityManager em) {
        SensorDevice sensorDevice = new SensorDevice();
        return sensorDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensorDevice createUpdatedEntity(EntityManager em) {
        SensorDevice sensorDevice = new SensorDevice();
        return sensorDevice;
    }

    @BeforeEach
    public void initTest() {
        sensorDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createSensorDevice() throws Exception {
        int databaseSizeBeforeCreate = sensorDeviceRepository.findAll().size();
        // Create the SensorDevice
        restSensorDeviceMockMvc.perform(post("/api/sensor-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sensorDevice)))
            .andExpect(status().isCreated());

        // Validate the SensorDevice in the database
        List<SensorDevice> sensorDeviceList = sensorDeviceRepository.findAll();
        assertThat(sensorDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        SensorDevice testSensorDevice = sensorDeviceList.get(sensorDeviceList.size() - 1);
    }

    @Test
    @Transactional
    public void createSensorDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sensorDeviceRepository.findAll().size();

        // Create the SensorDevice with an existing ID
        sensorDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorDeviceMockMvc.perform(post("/api/sensor-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sensorDevice)))
            .andExpect(status().isBadRequest());

        // Validate the SensorDevice in the database
        List<SensorDevice> sensorDeviceList = sensorDeviceRepository.findAll();
        assertThat(sensorDeviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSensorDevices() throws Exception {
        // Initialize the database
        sensorDeviceRepository.saveAndFlush(sensorDevice);

        // Get all the sensorDeviceList
        restSensorDeviceMockMvc.perform(get("/api/sensor-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorDevice.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSensorDevice() throws Exception {
        // Initialize the database
        sensorDeviceRepository.saveAndFlush(sensorDevice);

        // Get the sensorDevice
        restSensorDeviceMockMvc.perform(get("/api/sensor-devices/{id}", sensorDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensorDevice.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSensorDevice() throws Exception {
        // Get the sensorDevice
        restSensorDeviceMockMvc.perform(get("/api/sensor-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSensorDevice() throws Exception {
        // Initialize the database
        sensorDeviceRepository.saveAndFlush(sensorDevice);

        int databaseSizeBeforeUpdate = sensorDeviceRepository.findAll().size();

        // Update the sensorDevice
        SensorDevice updatedSensorDevice = sensorDeviceRepository.findById(sensorDevice.getId()).get();
        // Disconnect from session so that the updates on updatedSensorDevice are not directly saved in db
        em.detach(updatedSensorDevice);

        restSensorDeviceMockMvc.perform(put("/api/sensor-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSensorDevice)))
            .andExpect(status().isOk());

        // Validate the SensorDevice in the database
        List<SensorDevice> sensorDeviceList = sensorDeviceRepository.findAll();
        assertThat(sensorDeviceList).hasSize(databaseSizeBeforeUpdate);
        SensorDevice testSensorDevice = sensorDeviceList.get(sensorDeviceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSensorDevice() throws Exception {
        int databaseSizeBeforeUpdate = sensorDeviceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorDeviceMockMvc.perform(put("/api/sensor-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sensorDevice)))
            .andExpect(status().isBadRequest());

        // Validate the SensorDevice in the database
        List<SensorDevice> sensorDeviceList = sensorDeviceRepository.findAll();
        assertThat(sensorDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSensorDevice() throws Exception {
        // Initialize the database
        sensorDeviceRepository.saveAndFlush(sensorDevice);

        int databaseSizeBeforeDelete = sensorDeviceRepository.findAll().size();

        // Delete the sensorDevice
        restSensorDeviceMockMvc.perform(delete("/api/sensor-devices/{id}", sensorDevice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SensorDevice> sensorDeviceList = sensorDeviceRepository.findAll();
        assertThat(sensorDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
