package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.Shifts;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Shifts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShiftsRepository extends JpaRepository<Shifts, Long> {

}
