package com.diviso.newhrm.repository;

import com.diviso.newhrm.domain.Role;
import com.diviso.newhrm.service.dto.RoleDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select distinct role from Role role left join fetch role.peoples")
    List<Role> findAllWithEagerRelationships();

    @Query("select role from Role role left join fetch role.peoples where role.id =:id")
    Role findOneWithEagerRelationships(@Param("id") Long id);
    Page<Role>  findByReference(Long id,Pageable pageable);  
}
