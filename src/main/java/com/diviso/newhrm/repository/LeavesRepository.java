package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.Leaves;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Leaves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeavesRepository extends JpaRepository<Leaves, Long> {

}
