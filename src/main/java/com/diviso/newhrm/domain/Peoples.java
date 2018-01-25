package com.diviso.newhrm.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Peoples.
 */
@Entity
@Table(name = "peoples")
public class Peoples implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private Long reference;

    @ManyToOne
    private Shifts shifts;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return reference;
    }

    public Peoples reference(Long reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Shifts getShifts() {
        return shifts;
    }

    public Peoples shifts(Shifts shifts) {
        this.shifts = shifts;
        return this;
    }

    public void setShifts(Shifts shifts) {
        this.shifts = shifts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Peoples peoples = (Peoples) o;
        if (peoples.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peoples.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Peoples{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            "}";
    }
}
