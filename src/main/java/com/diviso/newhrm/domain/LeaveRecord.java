package com.diviso.newhrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LeaveRecord.
 */
@Entity
@Table(name = "leave_record")
public class LeaveRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "jhi_time")
    private LocalDate time;

    @ManyToOne
    private Leaves leaves;

    @OneToOne
    @JoinColumn(unique = true)
    private Peoples peoples;

    @OneToMany(mappedBy = "leaveRecord")
    @JsonIgnore
    private Set<Note> notes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LeaveRecord date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getTime() {
        return time;
    }

    public LeaveRecord time(LocalDate time) {
        this.time = time;
        return this;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Leaves getLeaves() {
        return leaves;
    }

    public LeaveRecord leaves(Leaves leaves) {
        this.leaves = leaves;
        return this;
    }

    public void setLeaves(Leaves leaves) {
        this.leaves = leaves;
    }

    public Peoples getPeoples() {
        return peoples;
    }

    public LeaveRecord peoples(Peoples peoples) {
        this.peoples = peoples;
        return this;
    }

    public void setPeoples(Peoples peoples) {
        this.peoples = peoples;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public LeaveRecord notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public LeaveRecord addNotes(Note note) {
        this.notes.add(note);
        note.setLeaveRecord(this);
        return this;
    }

    public LeaveRecord removeNotes(Note note) {
        this.notes.remove(note);
        note.setLeaveRecord(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
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
        LeaveRecord leaveRecord = (LeaveRecord) o;
        if (leaveRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveRecord{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
