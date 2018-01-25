package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.Peoples;
import com.diviso.newhrm.domain.Shifts;
import com.diviso.newhrm.service.dto.PeoplesDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Peoples entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeoplesRepository extends JpaRepository<Peoples, Long> {

	Page<Peoples> findByShifts_Id(Long id, Pageable pageable);
	
	Peoples findByReference(Long reference);
	
	
	
	
}
