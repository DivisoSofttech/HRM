package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.LeaveRecord;
import com.diviso.newhrm.service.dto.LeaveRecordDTO;

import org.springframework.stereotype.Repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LeaveRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Long> {

Page<LeaveRecord> findByDate(Date date,Pageable pageable);
	
Page<LeaveRecord> findByDateBetween(Date fromDate, Date toDate,Pageable pageable);
}
