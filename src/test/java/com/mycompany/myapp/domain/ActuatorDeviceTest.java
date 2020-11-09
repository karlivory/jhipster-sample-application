package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ActuatorDeviceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActuatorDevice.class);
        ActuatorDevice actuatorDevice1 = new ActuatorDevice();
        actuatorDevice1.setId(1L);
        ActuatorDevice actuatorDevice2 = new ActuatorDevice();
        actuatorDevice2.setId(actuatorDevice1.getId());
        assertThat(actuatorDevice1).isEqualTo(actuatorDevice2);
        actuatorDevice2.setId(2L);
        assertThat(actuatorDevice1).isNotEqualTo(actuatorDevice2);
        actuatorDevice1.setId(null);
        assertThat(actuatorDevice1).isNotEqualTo(actuatorDevice2);
    }
}
