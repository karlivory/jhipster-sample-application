package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CurrentReadingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentReading.class);
        CurrentReading currentReading1 = new CurrentReading();
        currentReading1.setId(1L);
        CurrentReading currentReading2 = new CurrentReading();
        currentReading2.setId(currentReading1.getId());
        assertThat(currentReading1).isEqualTo(currentReading2);
        currentReading2.setId(2L);
        assertThat(currentReading1).isNotEqualTo(currentReading2);
        currentReading1.setId(null);
        assertThat(currentReading1).isNotEqualTo(currentReading2);
    }
}
