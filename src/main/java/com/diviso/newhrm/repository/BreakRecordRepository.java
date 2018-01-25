package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.BreakRecord;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BreakRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreakRecordRepository extends JpaRepository<BreakRecord, Long> {
	
}
