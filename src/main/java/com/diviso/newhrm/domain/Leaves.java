package com.diviso.newhrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Leaves.
 */
@Entity
@Table(name = "leaves")
public class Leaves implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "no_of_days")
    private String noOfDays;

    @OneToMany(mappedBy = "leaves")
    @JsonIgnore
    private Set<LeaveRecord> leaveRecords = new HashSet<>();

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

    public Leaves name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Leaves description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public Leaves noOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
        return this;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Set<LeaveRecord> getLeaveRecords() {
        return leaveRecords;
    }

    public Leaves leaveRecords(Set<LeaveRecord> leaveRecords) {
        this.leaveRecords = leaveRecords;
        return this;
    }

    public Leaves addLeaveRecord(LeaveRecord leaveRecord) {
        this.leaveRecords.add(leaveRecord);
        leaveRecord.setLeaves(this);
        return this;
    }

    public Leaves removeLeaveRecord(LeaveRecord leaveRecord) {
        this.leaveRecords.remove(leaveRecord);
        leaveRecord.setLeaves(null);
        return this;
    }

    public void setLeaveRecords(Set<LeaveRecord> leaveRecords) {
        this.leaveRecords = leaveRecords;
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
        Leaves leaves = (Leaves) o;
        if (leaves.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaves.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Leaves{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", noOfDays='" + getNoOfDays() + "'" +
            "}";
    }
}
