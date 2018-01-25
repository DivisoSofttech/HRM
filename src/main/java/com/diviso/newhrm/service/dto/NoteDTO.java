package com.diviso.newhrm.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Note entity.
 */
public class NoteDTO implements Serializable {

    private Long id;

    private LocalDate dateOfCreation;

    private String matter;

    private Long breakRecordId;

    private Long leaveRecordId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public Long getBreakRecordId() {
        return breakRecordId;
    }

    public void setBreakRecordId(Long breakRecordId) {
        this.breakRecordId = breakRecordId;
    }

    public Long getLeaveRecordId() {
        return leaveRecordId;
    }

    public void setLeaveRecordId(Long leaveRecordId) {
        this.leaveRecordId = leaveRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NoteDTO noteDTO = (NoteDTO) o;
        if(noteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", matter='" + getMatter() + "'" +
            "}";
    }
}
