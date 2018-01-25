package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.PeoplesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Peoples and its DTO PeoplesDTO.
 */
@Mapper(componentModel = "spring", uses = {ShiftsMapper.class})
public interface PeoplesMapper extends EntityMapper<PeoplesDTO, Peoples> {

    @Mapping(source = "shiftsId", target = "shiftsId")
    PeoplesDTO toDto(PeoplesDTO peoples);

    @Mapping(source = "shiftsId", target = "shifts")
    Peoples toEntity(PeoplesDTO peoplesDTO);

    default Peoples fromId(Long id) {
        if (id == null) {
            return null;
        }
        Peoples peoples = new Peoples();
        peoples.setId(id);
        return peoples;
    }
}
