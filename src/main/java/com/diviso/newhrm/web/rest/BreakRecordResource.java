package com.diviso.newhrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.newhrm.domain.BreakRecord;
import com.diviso.newhrm.service.BreakRecordService;
import com.diviso.newhrm.web.rest.errors.BadRequestAlertException;
import com.diviso.newhrm.web.rest.util.HeaderUtil;
import com.diviso.newhrm.web.rest.util.PaginationUtil;
import com.diviso.newhrm.service.dto.BreakRecordDTO;
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
 * REST controller for managing BreakRecord.
 */
@RestController
@RequestMapping("/api")
public class BreakRecordResource {

    private final Logger log = LoggerFactory.getLogger(BreakRecordResource.class);

    private static final String ENTITY_NAME = "breakRecord";

    private final BreakRecordService breakRecordService;

    public BreakRecordResource(BreakRecordService breakRecordService) {
        this.breakRecordService = breakRecordService;
    }

    /**
     * POST  /break-records : Create a new breakRecord.
     *
     * @param breakRecordDTO the breakRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new breakRecordDTO, or with status 400 (Bad Request) if the breakRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/break-records")
    @Timed
    public ResponseEntity<BreakRecordDTO> createBreakRecord(@RequestBody BreakRecordDTO breakRecordDTO) throws URISyntaxException {
        log.debug("REST request to save BreakRecord : {}", breakRecordDTO);
        if (breakRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new breakRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BreakRecordDTO result = breakRecordService.save(breakRecordDTO);
        return ResponseEntity.created(new URI("/api/break-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /break-records : Updates an existing breakRecord.
     *
     * @param breakRecordDTO the breakRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated breakRecordDTO,
     * or with status 400 (Bad Request) if the breakRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the breakRecordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/break-records")
    @Timed
    public ResponseEntity<BreakRecordDTO> updateBreakRecord(@RequestBody BreakRecordDTO breakRecordDTO) throws URISyntaxException {
        log.debug("REST request to update BreakRecord : {}", breakRecordDTO);
        if (breakRecordDTO.getId() == null) {
            return createBreakRecord(breakRecordDTO);
        }
        BreakRecordDTO result = breakRecordService.save(breakRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, breakRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /break-records : get all the breakRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of breakRecords in body
     */
    @GetMapping("/break-records")
    @Timed
    public ResponseEntity<List<BreakRecordDTO>> getAllBreakRecords(Pageable pageable) {
        log.debug("REST request to get a page of BreakRecords");
        Page<BreakRecordDTO> page = breakRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/break-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /break-records/:id : get the "id" breakRecord.
     *
     * @param id the id of the breakRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the breakRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/break-records/{id}")
    @Timed
    public ResponseEntity<BreakRecordDTO> getBreakRecord(@PathVariable Long id) {
        log.debug("REST request to get BreakRecord : {}", id);
        BreakRecordDTO breakRecordDTO = breakRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(breakRecordDTO));
    }

    /**
     * DELETE  /break-records/:id : delete the "id" breakRecord.
     *
     * @param id the id of the breakRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/break-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteBreakRecord(@PathVariable Long id) {
        log.debug("REST request to delete BreakRecord : {}", id);
        breakRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    
   
}
