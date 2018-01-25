package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.LeaveRecordService;
import com.diviso.newhrm.domain.LeaveRecord;
import com.diviso.newhrm.repository.LeaveRecordRepository;
import com.diviso.newhrm.service.dto.LeaveRecordDTO;
import com.diviso.newhrm.service.mapper.LeaveRecordMapper;

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
 * Service Implementation for managing LeaveRecord.
 */
@Service
@Transactional
public class LeaveRecordServiceImpl implements LeaveRecordService {

    private final Logger log = LoggerFactory.getLogger(LeaveRecordServiceImpl.class);

    private final LeaveRecordRepository leaveRecordRepository;

    private final LeaveRecordMapper leaveRecordMapper;

    public LeaveRecordServiceImpl(LeaveRecordRepository leaveRecordRepository, LeaveRecordMapper leaveRecordMapper) {
        this.leaveRecordRepository = leaveRecordRepository;
        this.leaveRecordMapper = leaveRecordMapper;
    }

    /**
     * Save a leaveRecord.
     *
     * @param leaveRecordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LeaveRecordDTO save(LeaveRecordDTO leaveRecordDTO) {
        log.debug("Request to save LeaveRecord : {}", leaveRecordDTO);
        LeaveRecord leaveRecord = leaveRecordMapper.toEntity(leaveRecordDTO);
        leaveRecord = leaveRecordRepository.save(leaveRecord);
        return leaveRecordMapper.toDto(leaveRecord);
    }

    /**
     * Get all the leaveRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeaveRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveRecords");
        return leaveRecordRepository.findAll(pageable)
            .map(leaveRecordMapper::toDto);
    }

    /**
     * Get one leaveRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LeaveRecordDTO findOne(Long id) {
        log.debug("Request to get LeaveRecord : {}", id);
        LeaveRecord leaveRecord = leaveRecordRepository.findOne(id);
        return leaveRecordMapper.toDto(leaveRecord);
    }

    /**
     * Delete the leaveRecord by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeaveRecord : {}", id);
        leaveRecordRepository.delete(id);
    }
    
    
    /**
     * Get the leaveRecord by date.
     *
     * @param date 
     */
    
    @Override
    public Page<LeaveRecord> getDate(String from,Pageable pageable){
   	   Date date = null;
   	
   		try {
   			date = new SimpleDateFormat("dd-MM-yyyy").parse(from);
   	
   		} catch (ParseException e) {
   			e.printStackTrace();
   		}
   	   return leaveRecordRepository.findByDate(date,pageable);
      }
    /**
     * Get the leaveRecord between Two Dates.
     *
     * @param from, to
     */
	
	@Override
	public Page<LeaveRecord> getLeaveBetween(String from,String to,Pageable pageable){
	   	   Date fromDate = null;
	   		Date toDate = null;
	   		try {
	   			fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(from);
	   			toDate = new SimpleDateFormat("dd-MM-yyyy").parse(to);
	   		} catch (ParseException e) {
	   			e.printStackTrace();
	   		}
	   	   return leaveRecordRepository.findByDateBetween(fromDate, toDate,pageable);
	      }

	
}
