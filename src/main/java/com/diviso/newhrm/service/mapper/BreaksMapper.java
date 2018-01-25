package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.BreaksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Breaks and its DTO BreaksDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BreaksMapper extends EntityMapper<BreaksDTO, Breaks> {


    @Mapping(target = "breakRecords", ignore = true)
    Breaks toEntity(BreaksDTO breaksDTO);

    default Breaks fromId(Long id) {
        if (id == null) {
            return null;
        }
        Breaks breaks = new Breaks();
        breaks.setId(id);
        return breaks;
    }
}
