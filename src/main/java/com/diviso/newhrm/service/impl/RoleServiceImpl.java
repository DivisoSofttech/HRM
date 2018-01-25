package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.RoleService;
import com.diviso.newhrm.domain.Role;
import com.diviso.newhrm.repository.RoleRepository;
import com.diviso.newhrm.service.dto.RoleDTO;
import com.diviso.newhrm.service.mapper.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Role.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Save a role.
     *
     * @param roleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        log.debug("Request to save Role : {}", roleDTO);
        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return roleRepository.findAll(pageable)
            .map(roleMapper::toDto);
    }

    /**
     * Get one role by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RoleDTO findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        Role role = roleRepository.findOneWithEagerRelationships(id);
        return roleMapper.toDto(role);
    }

    /**
     * Delete the role by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.delete(id);
    }
    
    
    /**
     * Get the role by PeopleId.
     *
     * @param id 
     * @return roleList
     */

	@Override
	public Page<RoleDTO> getRoleByPeopleId(Long id,Pageable pageable) {
		 log.debug("Request to get getRoleByPeopleId : {}", id,pageable);
		 return roleRepository.findByReference(id,pageable).map(roleMapper::toDto);

	  	            		 
		 
		//List<RoleDTO> roleList = roleRepository.findByReference(id);

		 
  		//return roleList;
  		
  		
	}
}
