package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.ShiftsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shifts and its DTO ShiftsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShiftsMapper extends EntityMapper<ShiftsDTO, Shifts> {


    @Mapping(target = "peoples", ignore = true)
    Shifts toEntity(ShiftsDTO shiftsDTO);

    default Shifts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shifts shifts = new Shifts();
        shifts.setId(id);
        return shifts;
    }
}
