package com.diviso.newhrm.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.domain.Page;

import java.util.Objects;

/**
 * A DTO for the Peoples entity.
 */
public class PeoplesDTO implements Serializable {

    private Long id;

    private Long reference;

    private Long shiftsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Long getShiftsId() {
        return shiftsId;
    }

    public void setShiftsId(Long shiftsId) {
        this.shiftsId = shiftsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeoplesDTO peoplesDTO = (PeoplesDTO) o;
        if(peoplesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), peoplesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeoplesDTO{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            "}";
    }

	
}
