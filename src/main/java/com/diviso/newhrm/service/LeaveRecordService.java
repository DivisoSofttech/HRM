package com.diviso.newhrm.service;

import com.diviso.newhrm.domain.LeaveRecord;
import com.diviso.newhrm.service.dto.LeaveRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LeaveRecord.
 */
public interface LeaveRecordService {

    /**
     * Save a leaveRecord.
     *
     * @param leaveRecordDTO the entity to save
     * @return the persisted entity
     */
    LeaveRecordDTO save(LeaveRecordDTO leaveRecordDTO);

    /**
     * Get all the leaveRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveRecordDTO> findAll(Pageable pageable);

    /**
     * Get the "id" leaveRecord.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LeaveRecordDTO findOne(Long id);

    /**
     * Delete the "id" leaveRecord.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    
    /**
     * Get the "Date" leaveRecord.
     *
     * @param from
     * @return the entity
     */
    Page<LeaveRecord> getDate(String from,Pageable pageable);
	
	 /**
     * Get the "date" leaveRecord.
     *
     * @param from,to
     * @return the entity
     */
	Page<LeaveRecord> getLeaveBetween(String from, String to, Pageable pageable);
}
