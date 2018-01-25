package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.LeaveRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeaveRecord and its DTO LeaveRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {LeavesMapper.class, PeoplesMapper.class})
public interface LeaveRecordMapper extends EntityMapper<LeaveRecordDTO, LeaveRecord> {

    @Mapping(source = "leaves.id", target = "leavesId")
    @Mapping(source = "peoples.id", target = "peoplesId")
    LeaveRecordDTO toDto(LeaveRecord leaveRecord);

    @Mapping(source = "leavesId", target = "leaves")
    @Mapping(source = "peoplesId", target = "peoples")
    @Mapping(target = "notes", ignore = true)
    LeaveRecord toEntity(LeaveRecordDTO leaveRecordDTO);

    default LeaveRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setId(id);
        return leaveRecord;
    }
}
