package com.diviso.newhrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Breaks.
 */
@Entity
@Table(name = "breaks")
public class Breaks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_from")
    private LocalDate from;

    @Column(name = "till")
    private LocalDate till;

    @OneToMany(mappedBy = "breaks")
    @JsonIgnore
    private Set<BreakRecord> breakRecords = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Breaks name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Breaks description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFrom() {
        return from;
    }

    public Breaks from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTill() {
        return till;
    }

    public Breaks till(LocalDate till) {
        this.till = till;
        return this;
    }

    public void setTill(LocalDate till) {
        this.till = till;
    }

    public Set<BreakRecord> getBreakRecords() {
        return breakRecords;
    }

    public Breaks breakRecords(Set<BreakRecord> breakRecords) {
        this.breakRecords = breakRecords;
        return this;
    }

    public Breaks addBreakRecords(BreakRecord breakRecord) {
        this.breakRecords.add(breakRecord);
        breakRecord.setBreaks(this);
        return this;
    }

    public Breaks removeBreakRecords(BreakRecord breakRecord) {
        this.breakRecords.remove(breakRecord);
        breakRecord.setBreaks(null);
        return this;
    }

    public void setBreakRecords(Set<BreakRecord> breakRecords) {
        this.breakRecords = breakRecords;
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
        Breaks breaks = (Breaks) o;
        if (breaks.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), breaks.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Breaks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", from='" + getFrom() + "'" +
            ", till='" + getTill() + "'" +
            "}";
    }
}
