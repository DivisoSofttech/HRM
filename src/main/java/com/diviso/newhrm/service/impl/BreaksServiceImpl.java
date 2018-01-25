package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.BreaksService;
import com.diviso.newhrm.domain.Breaks;
import com.diviso.newhrm.repository.BreaksRepository;
import com.diviso.newhrm.service.dto.BreaksDTO;
import com.diviso.newhrm.service.mapper.BreaksMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Breaks.
 */
@Service
@Transactional
public class BreaksServiceImpl implements BreaksService {

    private final Logger log = LoggerFactory.getLogger(BreaksServiceImpl.class);

    private final BreaksRepository breaksRepository;

    private final BreaksMapper breaksMapper;

    public BreaksServiceImpl(BreaksRepository breaksRepository, BreaksMapper breaksMapper) {
        this.breaksRepository = breaksRepository;
        this.breaksMapper = breaksMapper;
    }

    /**
     * Save a breaks.
     *
     * @param breaksDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BreaksDTO save(BreaksDTO breaksDTO) {
        log.debug("Request to save Breaks : {}", breaksDTO);
        Breaks breaks = breaksMapper.toEntity(breaksDTO);
        breaks = breaksRepository.save(breaks);
        return breaksMapper.toDto(breaks);
    }

    /**
     * Get all the breaks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BreaksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Breaks");
        return breaksRepository.findAll(pageable)
            .map(breaksMapper::toDto);
    }

    /**
     * Get one breaks by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BreaksDTO findOne(Long id) {
        log.debug("Request to get Breaks : {}", id);
        Breaks breaks = breaksRepository.findOne(id);
        return breaksMapper.toDto(breaks);
    }

    /**
     * Delete the breaks by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Breaks : {}", id);
        breaksRepository.delete(id);
    }
    
    
   
    

	

	
	
}
