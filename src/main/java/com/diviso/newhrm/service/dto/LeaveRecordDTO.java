package com.diviso.newhrm.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LeaveRecord entity.
 */
public class LeaveRecordDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private LocalDate time;

    private Long leavesId;

    private Long peoplesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Long getLeavesId() {
        return leavesId;
    }

    public void setLeavesId(Long leavesId) {
        this.leavesId = leavesId;
    }

    public Long getPeoplesId() {
        return peoplesId;
    }

    public void setPeoplesId(Long peoplesId) {
        this.peoplesId = peoplesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaveRecordDTO leaveRecordDTO = (LeaveRecordDTO) o;
        if(leaveRecordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveRecordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveRecordDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
