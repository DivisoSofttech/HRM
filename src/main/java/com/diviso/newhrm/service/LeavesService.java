package com.diviso.newhrm.service;

import com.diviso.newhrm.service.dto.LeavesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Leaves.
 */
public interface LeavesService {

    /**
     * Save a leaves.
     *
     * @param leavesDTO the entity to save
     * @return the persisted entity
     */
    LeavesDTO save(LeavesDTO leavesDTO);

    /**
     * Get all the leaves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeavesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" leaves.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LeavesDTO findOne(Long id);

    /**
     * Delete the "id" leaves.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
