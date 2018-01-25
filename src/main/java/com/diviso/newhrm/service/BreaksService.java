package com.diviso.newhrm.service;

import com.diviso.newhrm.service.dto.BreaksDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Breaks.
 */
public interface BreaksService {

    /**
     * Save a breaks.
     *
     * @param breaksDTO the entity to save
     * @return the persisted entity
     */
    BreaksDTO save(BreaksDTO breaksDTO);

    /**
     * Get all the breaks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BreaksDTO> findAll(Pageable pageable);

    /**
     * Get the "id" breaks.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BreaksDTO findOne(Long id);

    /**
     * Delete the "id" breaks.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
}
