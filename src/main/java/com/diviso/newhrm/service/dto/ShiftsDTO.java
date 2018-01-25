package com.diviso.newhrm.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Shifts entity.
 */
public class ShiftsDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate from;

    private LocalDate till;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTill() {
        return till;
    }

    public void setTill(LocalDate till) {
        this.till = till;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShiftsDTO shiftsDTO = (ShiftsDTO) o;
        if(shiftsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shiftsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShiftsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", from='" + getFrom() + "'" +
            ", till='" + getTill() + "'" +
            "}";
    }
}
