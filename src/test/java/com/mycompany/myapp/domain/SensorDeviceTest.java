package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SensorDeviceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorDevice.class);
        SensorDevice sensorDevice1 = new SensorDevice();
        sensorDevice1.setId(1L);
        SensorDevice sensorDevice2 = new SensorDevice();
        sensorDevice2.setId(sensorDevice1.getId());
        assertThat(sensorDevice1).isEqualTo(sensorDevice2);
        sensorDevice2.setId(2L);
        assertThat(sensorDevice1).isNotEqualTo(sensorDevice2);
        sensorDevice1.setId(null);
        assertThat(sensorDevice1).isNotEqualTo(sensorDevice2);
    }
}
