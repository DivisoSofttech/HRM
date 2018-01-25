package com.diviso.newhrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.newhrm.service.BreaksService;
import com.diviso.newhrm.web.rest.errors.BadRequestAlertException;
import com.diviso.newhrm.web.rest.util.HeaderUtil;
import com.diviso.newhrm.web.rest.util.PaginationUtil;
import com.diviso.newhrm.service.dto.BreaksDTO;
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
 * REST controller for managing Breaks.
 */
@RestController
@RequestMapping("/api")
public class BreaksResource {

    private final Logger log = LoggerFactory.getLogger(BreaksResource.class);

    private static final String ENTITY_NAME = "breaks";

    private final BreaksService breaksService;

    public BreaksResource(BreaksService breaksService) {
        this.breaksService = breaksService;
    }

    /**
     * POST  /breaks : Create a new breaks.
     *
     * @param breaksDTO the breaksDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new breaksDTO, or with status 400 (Bad Request) if the breaks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/breaks")
    @Timed
    public ResponseEntity<BreaksDTO> createBreaks(@RequestBody BreaksDTO breaksDTO) throws URISyntaxException {
        log.debug("REST request to save Breaks : {}", breaksDTO);
        if (breaksDTO.getId() != null) {
            throw new BadRequestAlertException("A new breaks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BreaksDTO result = breaksService.save(breaksDTO);
        return ResponseEntity.created(new URI("/api/breaks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /breaks : Updates an existing breaks.
     *
     * @param breaksDTO the breaksDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated breaksDTO,
     * or with status 400 (Bad Request) if the breaksDTO is not valid,
     * or with status 500 (Internal Server Error) if the breaksDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/breaks")
    @Timed
    public ResponseEntity<BreaksDTO> updateBreaks(@RequestBody BreaksDTO breaksDTO) throws URISyntaxException {
        log.debug("REST request to update Breaks : {}", breaksDTO);
        if (breaksDTO.getId() == null) {
            return createBreaks(breaksDTO);
        }
        BreaksDTO result = breaksService.save(breaksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, breaksDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /breaks : get all the breaks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of breaks in body
     */
    @GetMapping("/breaks")
    @Timed
    public ResponseEntity<List<BreaksDTO>> getAllBreaks(Pageable pageable) {
        log.debug("REST request to get a page of Breaks");
        Page<BreaksDTO> page = breaksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/breaks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /breaks/:id : get the "id" breaks.
     *
     * @param id the id of the breaksDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the breaksDTO, or with status 404 (Not Found)
     */
    @GetMapping("/breaks/{id}")
    @Timed
    public ResponseEntity<BreaksDTO> getBreaks(@PathVariable Long id) {
        log.debug("REST request to get Breaks : {}", id);
        BreaksDTO breaksDTO = breaksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(breaksDTO));
    }

    /**
     * DELETE  /breaks/:id : delete the "id" breaks.
     *
     * @param id the id of the breaksDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/breaks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBreaks(@PathVariable Long id) {
        log.debug("REST request to delete Breaks : {}", id);
        breaksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    
    
   	
}
