package com.diviso.newhrm.service;

import com.diviso.newhrm.service.dto.NoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Note.
 */
public interface NoteService {

    /**
     * Save a note.
     *
     * @param noteDTO the entity to save
     * @return the persisted entity
     */
    NoteDTO save(NoteDTO noteDTO);

    /**
     * Get all the notes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NoteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" note.
     *
     * @param id the id of the entity
     * @return the entity
     */
    NoteDTO findOne(Long id);

    /**
     * Delete the "id" note.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
