package com.diviso.newhrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.newhrm.service.ShiftsService;
import com.diviso.newhrm.web.rest.errors.BadRequestAlertException;
import com.diviso.newhrm.web.rest.util.HeaderUtil;
import com.diviso.newhrm.web.rest.util.PaginationUtil;
import com.diviso.newhrm.service.dto.ShiftsDTO;
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
 * REST controller for managing Shifts.
 */
@RestController
@RequestMapping("/api")
public class ShiftsResource {

    private final Logger log = LoggerFactory.getLogger(ShiftsResource.class);

    private static final String ENTITY_NAME = "shifts";

    private final ShiftsService shiftsService;

    public ShiftsResource(ShiftsService shiftsService) {
        this.shiftsService = shiftsService;
    }

    /**
     * POST  /shifts : Create a new shifts.
     *
     * @param shiftsDTO the shiftsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shiftsDTO, or with status 400 (Bad Request) if the shifts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shifts")
    @Timed
    public ResponseEntity<ShiftsDTO> createShifts(@RequestBody ShiftsDTO shiftsDTO) throws URISyntaxException {
        log.debug("REST request to save Shifts : {}", shiftsDTO);
        if (shiftsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shifts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShiftsDTO result = shiftsService.save(shiftsDTO);
        return ResponseEntity.created(new URI("/api/shifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shifts : Updates an existing shifts.
     *
     * @param shiftsDTO the shiftsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shiftsDTO,
     * or with status 400 (Bad Request) if the shiftsDTO is not valid,
     * or with status 500 (Internal Server Error) if the shiftsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shifts")
    @Timed
    public ResponseEntity<ShiftsDTO> updateShifts(@RequestBody ShiftsDTO shiftsDTO) throws URISyntaxException {
        log.debug("REST request to update Shifts : {}", shiftsDTO);
        if (shiftsDTO.getId() == null) {
            return createShifts(shiftsDTO);
        }
        ShiftsDTO result = shiftsService.save(shiftsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shiftsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shifts : get all the shifts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shifts in body
     */
    @GetMapping("/shifts")
    @Timed
    public ResponseEntity<List<ShiftsDTO>> getAllShifts(Pageable pageable) {
        log.debug("REST request to get a page of Shifts");
        Page<ShiftsDTO> page = shiftsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shifts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shifts/:id : get the "id" shifts.
     *
     * @param id the id of the shiftsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shiftsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shifts/{id}")
    @Timed
    public ResponseEntity<ShiftsDTO> getShifts(@PathVariable Long id) {
        log.debug("REST request to get Shifts : {}", id);
        ShiftsDTO shiftsDTO = shiftsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shiftsDTO));
    }

    /**
     * DELETE  /shifts/:id : delete the "id" shifts.
     *
     * @param id the id of the shiftsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shifts/{id}")
    @Timed
    public ResponseEntity<Void> deleteShifts(@PathVariable Long id) {
        log.debug("REST request to delete Shifts : {}", id);
        shiftsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
