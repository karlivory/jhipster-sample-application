package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ReadingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reading.class);
        Reading reading1 = new Reading();
        reading1.setId(1L);
        Reading reading2 = new Reading();
        reading2.setId(reading1.getId());
        assertThat(reading1).isEqualTo(reading2);
        reading2.setId(2L);
        assertThat(reading1).isNotEqualTo(reading2);
        reading1.setId(null);
        assertThat(reading1).isNotEqualTo(reading2);
    }
}
