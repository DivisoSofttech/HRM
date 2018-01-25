package com.diviso.newhrm.service.impl;

import com.diviso.newhrm.service.ShiftsService;
import com.diviso.newhrm.domain.Shifts;
import com.diviso.newhrm.repository.ShiftsRepository;
import com.diviso.newhrm.service.dto.ShiftsDTO;
import com.diviso.newhrm.service.mapper.ShiftsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Shifts.
 */
@Service
@Transactional
public class ShiftsServiceImpl implements ShiftsService {

    private final Logger log = LoggerFactory.getLogger(ShiftsServiceImpl.class);

    private final ShiftsRepository shiftsRepository;

    private final ShiftsMapper shiftsMapper;

    public ShiftsServiceImpl(ShiftsRepository shiftsRepository, ShiftsMapper shiftsMapper) {
        this.shiftsRepository = shiftsRepository;
        this.shiftsMapper = shiftsMapper;
    }

    /**
     * Save a shifts.
     *
     * @param shiftsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShiftsDTO save(ShiftsDTO shiftsDTO) {
        log.debug("Request to save Shifts : {}", shiftsDTO);
        Shifts shifts = shiftsMapper.toEntity(shiftsDTO);
        shifts = shiftsRepository.save(shifts);
        return shiftsMapper.toDto(shifts);
    }

    /**
     * Get all the shifts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShiftsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shifts");
        return shiftsRepository.findAll(pageable)
            .map(shiftsMapper::toDto);
    }

    /**
     * Get one shifts by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShiftsDTO findOne(Long id) {
        log.debug("Request to get Shifts : {}", id);
        Shifts shifts = shiftsRepository.findOne(id);
        return shiftsMapper.toDto(shifts);
    }

    /**
     * Delete the shifts by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shifts : {}", id);
        shiftsRepository.delete(id);
    }
}
