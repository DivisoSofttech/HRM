package com.diviso.newhrm.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;

    @Column(name = "matter")
    private String matter;

    @ManyToOne
    private BreakRecord breakRecord;

    @ManyToOne
    private LeaveRecord leaveRecord;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public Note dateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getMatter() {
        return matter;
    }

    public Note matter(String matter) {
        this.matter = matter;
        return this;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public BreakRecord getBreakRecord() {
        return breakRecord;
    }

    public Note breakRecord(BreakRecord breakRecord) {
        this.breakRecord = breakRecord;
        return this;
    }

    public void setBreakRecord(BreakRecord breakRecord) {
        this.breakRecord = breakRecord;
    }

    public LeaveRecord getLeaveRecord() {
        return leaveRecord;
    }

    public Note leaveRecord(LeaveRecord leaveRecord) {
        this.leaveRecord = leaveRecord;
        return this;
    }

    public void setLeaveRecord(LeaveRecord leaveRecord) {
        this.leaveRecord = leaveRecord;
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
        Note note = (Note) o;
        if (note.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), note.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Note{" +
            "id=" + getId() +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", matter='" + getMatter() + "'" +
            "}";
    }
}
