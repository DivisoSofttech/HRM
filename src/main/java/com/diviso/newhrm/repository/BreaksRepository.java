package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.Breaks;
import com.diviso.newhrm.service.dto.BreaksDTO;

import org.springframework.stereotype.Repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Breaks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreaksRepository extends JpaRepository<Breaks, Long> {
	
}
