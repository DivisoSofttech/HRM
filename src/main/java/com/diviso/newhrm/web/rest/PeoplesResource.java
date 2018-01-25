package com.diviso.newhrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.diviso.newhrm.service.PeoplesService;
import com.diviso.newhrm.web.rest.errors.BadRequestAlertException;
import com.diviso.newhrm.web.rest.util.HeaderUtil;
import com.diviso.newhrm.web.rest.util.PaginationUtil;
import com.diviso.newhrm.service.dto.PeoplesDTO;
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
 * REST controller for managing Peoples.
 */
@RestController
@RequestMapping("/api")
public class PeoplesResource {

    private final Logger log = LoggerFactory.getLogger(PeoplesResource.class);

    private static final String ENTITY_NAME = "peoples";

    private final PeoplesService peoplesService;

    public PeoplesResource(PeoplesService peoplesService) {
        this.peoplesService = peoplesService;
    }

    /**
     * POST  /peoples : Create a new peoples.
     *
     * @param peoplesDTO the peoplesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new peoplesDTO, or with status 400 (Bad Request) if the peoples has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/peoples")
    @Timed
    public ResponseEntity<PeoplesDTO> createPeoples(@RequestBody PeoplesDTO peoplesDTO) throws URISyntaxException {
        log.debug("REST request to save Peoples : {}", peoplesDTO);
        if (peoplesDTO.getId() != null) {
            throw new BadRequestAlertException("A new peoples cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeoplesDTO result = peoplesService.save(peoplesDTO);
        return ResponseEntity.created(new URI("/api/peoples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /peoples : Updates an existing peoples.
     *
     * @param peoplesDTO the peoplesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated peoplesDTO,
     * or with status 400 (Bad Request) if the peoplesDTO is not valid,
     * or with status 500 (Internal Server Error) if the peoplesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/peoples")
    @Timed
    public ResponseEntity<PeoplesDTO> updatePeoples(@RequestBody PeoplesDTO peoplesDTO) throws URISyntaxException {
        log.debug("REST request to update Peoples : {}", peoplesDTO);
        if (peoplesDTO.getId() == null) {
            return createPeoples(peoplesDTO);
        }
        PeoplesDTO result = peoplesService.save(peoplesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, peoplesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /peoples : get all the peoples.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of peoples in body
     */
    @GetMapping("/peoples")
    @Timed
    public ResponseEntity<List<PeoplesDTO>> getAllPeoples(Pageable pageable) {
        log.debug("REST request to get a page of Peoples");
        Page<PeoplesDTO> page = peoplesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/peoples");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /peoples/:id : get the "id" peoples.
     *
     * @param id the id of the peoplesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the peoplesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/peoples/{id}")
    @Timed
    public ResponseEntity<PeoplesDTO> getPeoples(@PathVariable Long id) {
        log.debug("REST request to get Peoples : {}", id);
        PeoplesDTO peoplesDTO = peoplesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(peoplesDTO));
    }

    /**
     * DELETE  /peoples/:id : delete the "id" peoples.
     *
     * @param id the id of the peoplesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/peoples/{id}")
    @Timed
    public ResponseEntity<Void> deletePeoples(@PathVariable Long id) {
        log.debug("REST request to delete Peoples : {}", id);
        peoplesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
  
      
      
       
       /**
     	 * This method is used to findPeopleByShiftReference
     	 * @param id
     	 * @return peopleList
     	 */
    
       @GetMapping(value = "/peoples/findPeopleByShiftReference/{id}")
     	@Timed
     	public ResponseEntity<Page<PeoplesDTO>> findByPeoples_Shift_Reference(@PathVariable Long id,Pageable pageable) {
    	    log.debug("REST request to findPeopleByShiftReference : {}", id,pageable);
    	    Page<PeoplesDTO> peoplesDTO  = peoplesService.getPeopleByShiftReference(id,pageable);
     		 return ResponseUtil.wrapOrNotFound(Optional.ofNullable(peoplesDTO));
      }
}
