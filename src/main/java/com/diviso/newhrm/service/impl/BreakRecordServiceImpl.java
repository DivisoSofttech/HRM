package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.BreakRecordService;
import com.diviso.newhrm.domain.BreakRecord;
import com.diviso.newhrm.repository.BreakRecordRepository;
import com.diviso.newhrm.service.dto.BreakRecordDTO;
import com.diviso.newhrm.service.mapper.BreakRecordMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BreakRecord.
 */
@Service
@Transactional
public class BreakRecordServiceImpl implements BreakRecordService {

    private final Logger log = LoggerFactory.getLogger(BreakRecordServiceImpl.class);

    private final BreakRecordRepository breakRecordRepository;

    private final BreakRecordMapper breakRecordMapper;

    public BreakRecordServiceImpl(BreakRecordRepository breakRecordRepository, BreakRecordMapper breakRecordMapper) {
        this.breakRecordRepository = breakRecordRepository;
        this.breakRecordMapper = breakRecordMapper;
    }

    /**
     * Save a breakRecord.
     *
     * @param breakRecordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BreakRecordDTO save(BreakRecordDTO breakRecordDTO) {
        log.debug("Request to save BreakRecord : {}", breakRecordDTO);
        BreakRecord breakRecord = breakRecordMapper.toEntity(breakRecordDTO);
        breakRecord = breakRecordRepository.save(breakRecord);
        return breakRecordMapper.toDto(breakRecord);
    }

    /**
     * Get all the breakRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BreakRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BreakRecords");
        return breakRecordRepository.findAll(pageable)
            .map(breakRecordMapper::toDto);
    }

    /**
     * Get one breakRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BreakRecordDTO findOne(Long id) {
        log.debug("Request to get BreakRecord : {}", id);
        BreakRecord breakRecord = breakRecordRepository.findOne(id);
        return breakRecordMapper.toDto(breakRecord);
    }

    /**
     * Delete the breakRecord by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BreakRecord : {}", id);
        breakRecordRepository.delete(id);
    }
    
    
    /**
     * Get the breakRecord by Two Date.
     *
     * @param from,to
     */
     
   
}
