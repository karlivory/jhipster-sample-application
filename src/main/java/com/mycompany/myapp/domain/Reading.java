package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.mycompany.myapp.domain.enumeration.ReadingType;

/**
 * A Reading.
 */
@Entity
@Table(name = "reading")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReadingType type;

    @Column(name = "value")
    private Double value;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @ManyToOne
    @JsonIgnoreProperties(value = "readings", allowSetters = true)
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReadingType getType() {
        return type;
    }

    public Reading type(ReadingType type) {
        this.type = type;
        return this;
    }

    public void setType(ReadingType type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public Reading value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Reading timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Device getDevice() {
        return device;
    }

    public Reading device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reading)) {
            return false;
        }
        return id != null && id.equals(((Reading) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reading{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
