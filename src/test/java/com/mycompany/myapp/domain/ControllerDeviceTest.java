package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ControllerDeviceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControllerDevice.class);
        ControllerDevice controllerDevice1 = new ControllerDevice();
        controllerDevice1.setId(1L);
        ControllerDevice controllerDevice2 = new ControllerDevice();
        controllerDevice2.setId(controllerDevice1.getId());
        assertThat(controllerDevice1).isEqualTo(controllerDevice2);
        controllerDevice2.setId(2L);
        assertThat(controllerDevice1).isNotEqualTo(controllerDevice2);
        controllerDevice1.setId(null);
        assertThat(controllerDevice1).isNotEqualTo(controllerDevice2);
    }
}
