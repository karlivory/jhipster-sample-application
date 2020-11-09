package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ControllerDevice;
import com.mycompany.myapp.repository.ControllerDeviceRepository;

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
 * Integration tests for the {@link ControllerDeviceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ControllerDeviceResourceIT {

    @Autowired
    private ControllerDeviceRepository controllerDeviceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControllerDeviceMockMvc;

    private ControllerDevice controllerDevice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControllerDevice createEntity(EntityManager em) {
        ControllerDevice controllerDevice = new ControllerDevice();
        return controllerDevice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControllerDevice createUpdatedEntity(EntityManager em) {
        ControllerDevice controllerDevice = new ControllerDevice();
        return controllerDevice;
    }

    @BeforeEach
    public void initTest() {
        controllerDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createControllerDevice() throws Exception {
        int databaseSizeBeforeCreate = controllerDeviceRepository.findAll().size();
        // Create the ControllerDevice
        restControllerDeviceMockMvc.perform(post("/api/controller-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controllerDevice)))
            .andExpect(status().isCreated());

        // Validate the ControllerDevice in the database
        List<ControllerDevice> controllerDeviceList = controllerDeviceRepository.findAll();
        assertThat(controllerDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        ControllerDevice testControllerDevice = controllerDeviceList.get(controllerDeviceList.size() - 1);
    }

    @Test
    @Transactional
    public void createControllerDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controllerDeviceRepository.findAll().size();

        // Create the ControllerDevice with an existing ID
        controllerDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControllerDeviceMockMvc.perform(post("/api/controller-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controllerDevice)))
            .andExpect(status().isBadRequest());

        // Validate the ControllerDevice in the database
        List<ControllerDevice> controllerDeviceList = controllerDeviceRepository.findAll();
        assertThat(controllerDeviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllControllerDevices() throws Exception {
        // Initialize the database
        controllerDeviceRepository.saveAndFlush(controllerDevice);

        // Get all the controllerDeviceList
        restControllerDeviceMockMvc.perform(get("/api/controller-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controllerDevice.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getControllerDevice() throws Exception {
        // Initialize the database
        controllerDeviceRepository.saveAndFlush(controllerDevice);

        // Get the controllerDevice
        restControllerDeviceMockMvc.perform(get("/api/controller-devices/{id}", controllerDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controllerDevice.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingControllerDevice() throws Exception {
        // Get the controllerDevice
        restControllerDeviceMockMvc.perform(get("/api/controller-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControllerDevice() throws Exception {
        // Initialize the database
        controllerDeviceRepository.saveAndFlush(controllerDevice);

        int databaseSizeBeforeUpdate = controllerDeviceRepository.findAll().size();

        // Update the controllerDevice
        ControllerDevice updatedControllerDevice = controllerDeviceRepository.findById(controllerDevice.getId()).get();
        // Disconnect from session so that the updates on updatedControllerDevice are not directly saved in db
        em.detach(updatedControllerDevice);

        restControllerDeviceMockMvc.perform(put("/api/controller-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedControllerDevice)))
            .andExpect(status().isOk());

        // Validate the ControllerDevice in the database
        List<ControllerDevice> controllerDeviceList = controllerDeviceRepository.findAll();
        assertThat(controllerDeviceList).hasSize(databaseSizeBeforeUpdate);
        ControllerDevice testControllerDevice = controllerDeviceList.get(controllerDeviceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingControllerDevice() throws Exception {
        int databaseSizeBeforeUpdate = controllerDeviceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControllerDeviceMockMvc.perform(put("/api/controller-devices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controllerDevice)))
            .andExpect(status().isBadRequest());

        // Validate the ControllerDevice in the database
        List<ControllerDevice> controllerDeviceList = controllerDeviceRepository.findAll();
        assertThat(controllerDeviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteControllerDevice() throws Exception {
        // Initialize the database
        controllerDeviceRepository.saveAndFlush(controllerDevice);

        int databaseSizeBeforeDelete = controllerDeviceRepository.findAll().size();

        // Delete the controllerDevice
        restControllerDeviceMockMvc.perform(delete("/api/controller-devices/{id}", controllerDevice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControllerDevice> controllerDeviceList = controllerDeviceRepository.findAll();
        assertThat(controllerDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
