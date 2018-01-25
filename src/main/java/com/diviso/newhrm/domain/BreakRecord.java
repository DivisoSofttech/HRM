package com.diviso.newhrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BreakRecord.
 */
@Entity
@Table(name = "break_record")
public class BreakRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "jhi_time")
    private LocalDate time;

    @ManyToOne
    private Breaks breaks;

    @OneToOne
    @JoinColumn(unique = true)
    private Peoples peoples;

    @OneToMany(mappedBy = "breakRecord")
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

    public BreakRecord date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getTime() {
        return time;
    }

    public BreakRecord time(LocalDate time) {
        this.time = time;
        return this;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Breaks getBreaks() {
        return breaks;
    }

    public BreakRecord breaks(Breaks breaks) {
        this.breaks = breaks;
        return this;
    }

    public void setBreaks(Breaks breaks) {
        this.breaks = breaks;
    }

    public Peoples getPeoples() {
        return peoples;
    }

    public BreakRecord peoples(Peoples peoples) {
        this.peoples = peoples;
        return this;
    }

    public void setPeoples(Peoples peoples) {
        this.peoples = peoples;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public BreakRecord notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public BreakRecord addNotes(Note note) {
        this.notes.add(note);
        note.setBreakRecord(this);
        return this;
    }

    public BreakRecord removeNotes(Note note) {
        this.notes.remove(note);
        note.setBreakRecord(null);
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
        BreakRecord breakRecord = (BreakRecord) o;
        if (breakRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), breakRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BreakRecord{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
