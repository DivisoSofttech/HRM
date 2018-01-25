package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.NoteService;
import com.diviso.newhrm.domain.Note;
import com.diviso.newhrm.repository.NoteRepository;
import com.diviso.newhrm.service.dto.NoteDTO;
import com.diviso.newhrm.service.mapper.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Note.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final Logger log = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public NoteDTO save(NoteDTO noteDTO) {
        log.debug("Request to save Note : {}", noteDTO);
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notes");
        return noteRepository.findAll(pageable)
            .map(noteMapper::toDto);
    }

    /**
     * Get one note by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NoteDTO findOne(Long id) {
        log.debug("Request to get Note : {}", id);
        Note note = noteRepository.findOne(id);
        return noteMapper.toDto(note);
    }

    /**
     * Delete the note by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Note : {}", id);
        noteRepository.delete(id);
    }
}
