package com.diviso.newhrm.service;


import com.diviso.newhrm.service.dto.BreakRecordDTO;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BreakRecord.
 */
public interface BreakRecordService {

    /**
     * Save a breakRecord.
     *
     * @param breakRecordDTO the entity to save
     * @return the persisted entity
     */
    BreakRecordDTO save(BreakRecordDTO breakRecordDTO);

    /**
     * Get all the breakRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BreakRecordDTO> findAll(Pageable pageable);

    /**
     * Get the "id" breakRecord.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BreakRecordDTO findOne(Long id);

    /**
     * Delete the "id" breakRecord.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Get  the breakRecords using Two Date.
     *
     * @return the list of entities
     */

}
