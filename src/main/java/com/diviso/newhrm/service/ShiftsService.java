package com.diviso.newhrm.service;

import com.diviso.newhrm.service.dto.ShiftsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Shifts.
 */
public interface ShiftsService {

    /**
     * Save a shifts.
     *
     * @param shiftsDTO the entity to save
     * @return the persisted entity
     */
    ShiftsDTO save(ShiftsDTO shiftsDTO);

    /**
     * Get all the shifts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShiftsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shifts.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShiftsDTO findOne(Long id);

    /**
     * Delete the "id" shifts.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
