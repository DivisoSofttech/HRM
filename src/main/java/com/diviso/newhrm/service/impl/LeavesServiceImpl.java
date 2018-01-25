package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.LeavesService;
import com.diviso.newhrm.domain.Leaves;
import com.diviso.newhrm.repository.LeavesRepository;
import com.diviso.newhrm.service.dto.LeavesDTO;
import com.diviso.newhrm.service.mapper.LeavesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Leaves.
 */
@Service
@Transactional
public class LeavesServiceImpl implements LeavesService {

    private final Logger log = LoggerFactory.getLogger(LeavesServiceImpl.class);

    private final LeavesRepository leavesRepository;

    private final LeavesMapper leavesMapper;

    public LeavesServiceImpl(LeavesRepository leavesRepository, LeavesMapper leavesMapper) {
        this.leavesRepository = leavesRepository;
        this.leavesMapper = leavesMapper;
    }

    /**
     * Save a leaves.
     *
     * @param leavesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LeavesDTO save(LeavesDTO leavesDTO) {
        log.debug("Request to save Leaves : {}", leavesDTO);
        Leaves leaves = leavesMapper.toEntity(leavesDTO);
        leaves = leavesRepository.save(leaves);
        return leavesMapper.toDto(leaves);
    }

    /**
     * Get all the leaves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeavesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leaves");
        return leavesRepository.findAll(pageable)
            .map(leavesMapper::toDto);
    }

    /**
     * Get one leaves by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LeavesDTO findOne(Long id) {
        log.debug("Request to get Leaves : {}", id);
        Leaves leaves = leavesRepository.findOne(id);
        return leavesMapper.toDto(leaves);
    }

    /**
     * Delete the leaves by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Leaves : {}", id);
        leavesRepository.delete(id);
    }
}
