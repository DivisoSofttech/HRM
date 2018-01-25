package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.PeoplesService;
import com.diviso.newhrm.domain.Peoples;
import com.diviso.newhrm.repository.PeoplesRepository;
import com.diviso.newhrm.service.dto.PeoplesDTO;
import com.diviso.newhrm.service.mapper.PeoplesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Peoples.
 */
@Service
@Transactional
public class PeoplesServiceImpl implements PeoplesService {

    private final Logger log = LoggerFactory.getLogger(PeoplesServiceImpl.class);

    private final PeoplesRepository peoplesRepository;

    private final PeoplesMapper peoplesMapper;

    public PeoplesServiceImpl(PeoplesRepository peoplesRepository, PeoplesMapper peoplesMapper) {
        this.peoplesRepository = peoplesRepository;
        this.peoplesMapper = peoplesMapper;
    }

    /**
     * Save a peoples.
     *
     * @param peoplesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PeoplesDTO save(PeoplesDTO peoplesDTO) {
        log.debug("Request to save Peoples : {}", peoplesDTO);
        Peoples peoples = peoplesMapper.toEntity(peoplesDTO);
        peoples = peoplesRepository.save(peoples);
        return peoplesMapper.toDto(peoples);
    }

    /**
     * Get all the peoples.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PeoplesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Peoples");
        return peoplesRepository.findAll(pageable)
            .map(peoplesMapper::toDto);
    }

    /**
     * Get one peoples by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PeoplesDTO findOne(Long id) {
        log.debug("Request to get Peoples : {}", id);
        Peoples peoples = peoplesRepository.findOne(id);
        return peoplesMapper.toDto(peoples);
    }

    /**
     * Delete the peoples by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Peoples : {}", id);
        peoplesRepository.delete(id);
    }
    
  
	
	/**
     * Get the Shift byPeopleIid.
     *
     * @param id 
     * return shiftList
     */
	
	@Override
	public PeoplesDTO getShiftByPeoplesReference(Long id) {
		
		
		//return peoplesRepository.findByReference(id);
        //.map(peoplesMapper::toDto);
		//List<PeoplesDTO> shiftList = peoplesRepository.findByReference(id);
  		//return shiftList;
        Peoples peoples = peoplesRepository.findByReference(id);
        return peoplesMapper.toDto(peoples);
	}
    
	/**
     * Get the People byShiftIid.
     *
     * @param id 
     * return peopleList
     */
	

	@Override
	public Page<PeoplesDTO>  getPeopleByShiftReference(Long id,Pageable pageable) {
		//List<PeoplesDTO> peopleList = peoplesRepository.findByReference(id);
  		//return peopleList;
		Page<Peoples> people=peoplesRepository.findByShifts_Id(id,pageable);
		return people.map(peoplesMapper::toDto);
  	            
	}

	
	

	
}
