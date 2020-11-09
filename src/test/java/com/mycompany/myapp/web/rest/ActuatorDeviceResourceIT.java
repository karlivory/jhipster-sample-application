package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ActuatorDevice;
import com.mycompany.myapp.repository.ActuatorDeviceRepository;

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
 * Integration tests for the {@link ActuatorDeviceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActuatorDeviceResourceIT {

    @Autowired
    private ActuatorDeviceRepository actuatorDeviceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActuatorDeviceMockMvc;

    private ActuatorDevice actuatorDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActuatorDevice createEntity(EntityManager em) {
        ActuatorDevice actuatorDevice = new ActuatorDevice();
        return actuatorDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActuatorDevice createUpdatedEntity(EntityManager em) {
        ActuatorDevice actuatorDevice = new ActuatorDevice();
        return actuatorDevice;
    }

    @BeforeEach
    public void initTest() {
        actuatorDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createActuatorDevice() throws Exception {
        int databaseSizeBeforeCreate = actuatorDeviceRepository.findAll().size();
        // Create the ActuatorDevice
        restActuatorDeviceMockMvc.perform(post("/api/actuator-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actuatorDevice)))
            .andExpect(status().isCreated());

        // Validate the ActuatorDevice in the database
        List<ActuatorDevice> actuatorDeviceList = actuatorDeviceRepository.findAll();
        assertThat(actuatorDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        ActuatorDevice testActuatorDevice = actuatorDeviceList.get(actuatorDeviceList.size() - 1);
    }

    @Test
    @Transactional
    public void createActuatorDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actuatorDeviceRepository.findAll().size();

        // Create the ActuatorDevice with an existing ID
        actuatorDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActuatorDeviceMockMvc.perform(post("/api/actuator-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actuatorDevice)))
            .andExpect(status().isBadRequest());

        // Validate the ActuatorDevice in the database
        List<ActuatorDevice> actuatorDeviceList = actuatorDeviceRepository.findAll();
        assertThat(actuatorDeviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActuatorDevices() throws Exception {
        // Initialize the database
        actuatorDeviceRepository.saveAndFlush(actuatorDevice);

        // Get all the actuatorDeviceList
        restActuatorDeviceMockMvc.perform(get("/api/actuator-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actuatorDevice.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getActuatorDevice() throws Exception {
        // Initialize the database
        actuatorDeviceRepository.saveAndFlush(actuatorDevice);

        // Get the actuatorDevice
        restActuatorDeviceMockMvc.perform(get("/api/actuator-devices/{id}", actuatorDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actuatorDevice.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingActuatorDevice() throws Exception {
        // Get the actuatorDevice
        restActuatorDeviceMockMvc.perform(get("/api/actuator-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActuatorDevice() throws Exception {
        // Initialize the database
        actuatorDeviceRepository.saveAndFlush(actuatorDevice);

        int databaseSizeBeforeUpdate = actuatorDeviceRepository.findAll().size();

        // Update the actuatorDevice
        ActuatorDevice updatedActuatorDevice = actuatorDeviceRepository.findById(actuatorDevice.getId()).get();
        // Disconnect from session so that the updates on updatedActuatorDevice are not directly saved in db
        em.detach(updatedActuatorDevice);

        restActuatorDeviceMockMvc.perform(put("/api/actuator-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActuatorDevice)))
            .andExpect(status().isOk());

        // Validate the ActuatorDevice in the database
        List<ActuatorDevice> actuatorDeviceList = actuatorDeviceRepository.findAll();
        assertThat(actuatorDeviceList).hasSize(databaseSizeBeforeUpdate);
        ActuatorDevice testActuatorDevice = actuatorDeviceList.get(actuatorDeviceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingActuatorDevice() throws Exception {
        int databaseSizeBeforeUpdate = actuatorDeviceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActuatorDeviceMockMvc.perform(put("/api/actuator-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actuatorDevice)))
            .andExpect(status().isBadRequest());

        // Validate the ActuatorDevice in the database
        List<ActuatorDevice> actuatorDeviceList = actuatorDeviceRepository.findAll();
        assertThat(actuatorDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActuatorDevice() throws Exception {
        // Initialize the database
        actuatorDeviceRepository.saveAndFlush(actuatorDevice);

        int databaseSizeBeforeDelete = actuatorDeviceRepository.findAll().size();

        // Delete the actuatorDevice
        restActuatorDeviceMockMvc.perform(delete("/api/actuator-devices/{id}", actuatorDevice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActuatorDevice> actuatorDeviceList = actuatorDeviceRepository.findAll();
        assertThat(actuatorDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
