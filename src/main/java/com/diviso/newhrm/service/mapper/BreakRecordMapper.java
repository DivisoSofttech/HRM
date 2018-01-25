package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.BreakRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BreakRecord and its DTO BreakRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {BreaksMapper.class, PeoplesMapper.class})
public interface BreakRecordMapper extends EntityMapper<BreakRecordDTO, BreakRecord> {

    @Mapping(source = "breaks.id", target = "breaksId")
    @Mapping(source = "peoples.id", target = "peoplesId")
    BreakRecordDTO toDto(BreakRecord breakRecord);

    @Mapping(source = "breaksId", target = "breaks")
    @Mapping(source = "peoplesId", target = "peoples")
    @Mapping(target = "notes", ignore = true)
    BreakRecord toEntity(BreakRecordDTO breakRecordDTO);

    default BreakRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        BreakRecord breakRecord = new BreakRecord();
        breakRecord.setId(id);
        return breakRecord;
    }
}
