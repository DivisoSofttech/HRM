package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.LeavesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Leaves and its DTO LeavesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeavesMapper extends EntityMapper<LeavesDTO, Leaves> {


    @Mapping(target = "leaveRecords", ignore = true)
    Leaves toEntity(LeavesDTO leavesDTO);

    default Leaves fromId(Long id) {
        if (id == null) {
            return null;
        }
        Leaves leaves = new Leaves();
        leaves.setId(id);
        return leaves;
    }
}
