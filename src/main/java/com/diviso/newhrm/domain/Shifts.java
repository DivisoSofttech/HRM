package com.diviso.newhrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Shifts.
 */
@Entity
@Table(name = "shifts")
public class Shifts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_from")
    private LocalDate from;

    @Column(name = "till")
    private LocalDate till;

    @OneToMany(mappedBy = "shifts")
    @JsonIgnore
    private Set<Peoples> peoples = new HashSet<>();

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

    public Shifts name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFrom() {
        return from;
    }

    public Shifts from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTill() {
        return till;
    }

    public Shifts till(LocalDate till) {
        this.till = till;
        return this;
    }

    public void setTill(LocalDate till) {
        this.till = till;
    }

    public Set<Peoples> getPeoples() {
        return peoples;
    }

    public Shifts peoples(Set<Peoples> peoples) {
        this.peoples = peoples;
        return this;
    }

    public Shifts addPeoples(Peoples peoples) {
        this.peoples.add(peoples);
        peoples.setShifts(this);
        return this;
    }

    public Shifts removePeoples(Peoples peoples) {
        this.peoples.remove(peoples);
        peoples.setShifts(null);
        return this;
    }

    public void setPeoples(Set<Peoples> peoples) {
        this.peoples = peoples;
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
        Shifts shifts = (Shifts) o;
        if (shifts.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shifts.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shifts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", from='" + getFrom() + "'" +
            ", till='" + getTill() + "'" +
            "}";
    }
}
