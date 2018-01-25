package com.diviso.newhrm.service;

import com.diviso.newhrm.service.dto.PeoplesDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Peoples.
 */
public interface PeoplesService {

    /**
     * Save a peoples.
     *
     * @param peoplesDTO the entity to save
     * @return the persisted entity
     */
    PeoplesDTO save(PeoplesDTO peoplesDTO);

    /**
     * Get all the peoples.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeoplesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" peoples.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PeoplesDTO findOne(Long id);

    /**
     * Delete the "id" peoples.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    

    Page<PeoplesDTO> getPeopleByShiftReference(Long id,Pageable pageable);
	
	PeoplesDTO getShiftByPeoplesReference(Long id);

	//Page<PeoplesDTO> getPeoplesByRoleId(Long roleId, Pageable pageable);

	//Page<PeoplesDTO> getfindPeopleByRoleId(Long roleId, Pageable pageable);

	
	
	
}
