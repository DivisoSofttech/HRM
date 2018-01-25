package com.diviso.newhrm.service.mapper;

import com.diviso.newhrm.domain.*;
import com.diviso.newhrm.service.dto.NoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Note and its DTO NoteDTO.
 */
@Mapper(componentModel = "spring", uses = {BreakRecordMapper.class, LeaveRecordMapper.class})
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

    @Mapping(source = "breakRecord.id", target = "breakRecordId")
    @Mapping(source = "leaveRecord.id", target = "leaveRecordId")
    NoteDTO toDto(Note note);

    @Mapping(source = "breakRecordId", target = "breakRecord")
    @Mapping(source = "leaveRecordId", target = "leaveRecord")
    Note toEntity(NoteDTO noteDTO);

    default Note fromId(Long id) {
        if (id == null) {
            return null;
        }
        Note note = new Note();
        note.setId(id);
        return note;
    }
}
