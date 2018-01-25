package com.diviso.newhrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.newhrm.domain.LeaveRecord;
import com.diviso.newhrm.service.LeaveRecordService;
import com.diviso.newhrm.web.rest.errors.BadRequestAlertException;
import com.diviso.newhrm.web.rest.util.HeaderUtil;
import com.diviso.newhrm.web.rest.util.PaginationUtil;
import com.diviso.newhrm.service.dto.LeaveRecordDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LeaveRecord.
 */
@RestController
@RequestMapping("/api")
public class LeaveRecordResource {

    private final Logger log = LoggerFactory.getLogger(LeaveRecordResource.class);

    private static final String ENTITY_NAME = "leaveRecord";

    private final LeaveRecordService leaveRecordService;

    public LeaveRecordResource(LeaveRecordService leaveRecordService) {
        this.leaveRecordService = leaveRecordService;
    }

    /**
     * POST  /leave-records : Create a new leaveRecord.
     *
     * @param leaveRecordDTO the leaveRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaveRecordDTO, or with status 400 (Bad Request) if the leaveRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leave-records")
    @Timed
    public ResponseEntity<LeaveRecordDTO> createLeaveRecord(@RequestBody LeaveRecordDTO leaveRecordDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveRecord : {}", leaveRecordDTO);
        if (leaveRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveRecordDTO result = leaveRecordService.save(leaveRecordDTO);
        return ResponseEntity.created(new URI("/api/leave-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leave-records : Updates an existing leaveRecord.
     *
     * @param leaveRecordDTO the leaveRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaveRecordDTO,
     * or with status 400 (Bad Request) if the leaveRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaveRecordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leave-records")
    @Timed
    public ResponseEntity<LeaveRecordDTO> updateLeaveRecord(@RequestBody LeaveRecordDTO leaveRecordDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveRecord : {}", leaveRecordDTO);
        if (leaveRecordDTO.getId() == null) {
            return createLeaveRecord(leaveRecordDTO);
        }
        LeaveRecordDTO result = leaveRecordService.save(leaveRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leave-records : get all the leaveRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leaveRecords in body
     */
    @GetMapping("/leave-records")
    @Timed
    public ResponseEntity<List<LeaveRecordDTO>> getAllLeaveRecords(Pageable pageable) {
        log.debug("REST request to get a page of LeaveRecords");
        Page<LeaveRecordDTO> page = leaveRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leave-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /leave-records/:id : get the "id" leaveRecord.
     *
     * @param id the id of the leaveRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaveRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leave-records/{id}")
    @Timed
    public ResponseEntity<LeaveRecordDTO> getLeaveRecord(@PathVariable Long id) {
        log.debug("REST request to get LeaveRecord : {}", id);
        LeaveRecordDTO leaveRecordDTO = leaveRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leaveRecordDTO));
    }

    /**
     * DELETE  /leave-records/:id : delete the "id" leaveRecord.
     *
     * @param id the id of the leaveRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeaveRecord(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRecord : {}", id);
        leaveRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    
    
    
    /**
     * This method is used to find leaves using to date search
     * 
     * @param from
     * @return from
     */
    @GetMapping(value = "/leave-records/date/{from}")
   	@Timed
   	public Page<LeaveRecord> dateToDateSearch(@PathVariable String from,@PathVariable Pageable pageable) {
    	log.debug("REST request to delete LeaveRecord : {}", from,pageable);
   		return leaveRecordService.getDate(from,pageable);
   	}
    /**
     * This method is used to find leaves using  two dates
     * 
     * @param from
     * @param to
     * @return from,to
     */
    @GetMapping(value = "/leaveRecord/dateToDate/{from}/{to}")
   	@Timed
   	public Page<LeaveRecord> dateToDateSearch(@PathVariable String from, @PathVariable String to,Pageable pageable) {
    	log.debug("REST request to delete LeaveRecord : {}", from,to,pageable);
   		return leaveRecordService.getLeaveBetween(from, to,pageable);
   	}
}
