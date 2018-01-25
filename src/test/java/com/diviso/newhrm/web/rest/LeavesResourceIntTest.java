package com.diviso.newhrm.web.rest;

import com.diviso.newhrm.NewhrmApp;

import com.diviso.newhrm.domain.Leaves;
import com.diviso.newhrm.repository.LeavesRepository;
import com.diviso.newhrm.service.LeavesService;
import com.diviso.newhrm.service.dto.LeavesDTO;
import com.diviso.newhrm.service.mapper.LeavesMapper;
import com.diviso.newhrm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.diviso.newhrm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LeavesResource REST controller.
 *
 * @see LeavesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewhrmApp.class)
public class LeavesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NO_OF_DAYS = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_DAYS = "BBBBBBBBBB";

    @Autowired
    private LeavesRepository leavesRepository;

    @Autowired
    private LeavesMapper leavesMapper;

    @Autowired
    private LeavesService leavesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeavesMockMvc;

    private Leaves leaves;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeavesResource leavesResource = new LeavesResource(leavesService);
        this.restLeavesMockMvc = MockMvcBuilders.standaloneSetup(leavesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leaves createEntity(EntityManager em) {
        Leaves leaves = new Leaves()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .noOfDays(DEFAULT_NO_OF_DAYS);
        return leaves;
    }

    @Before
    public void initTest() {
        leaves = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaves() throws Exception {
        int databaseSizeBeforeCreate = leavesRepository.findAll().size();

        // Create the Leaves
        LeavesDTO leavesDTO = leavesMapper.toDto(leaves);
        restLeavesMockMvc.perform(post("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leavesDTO)))
            .andExpect(status().isCreated());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeCreate + 1);
        Leaves testLeaves = leavesList.get(leavesList.size() - 1);
        assertThat(testLeaves.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaves.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLeaves.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
    }

    @Test
    @Transactional
    public void createLeavesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leavesRepository.findAll().size();

        // Create the Leaves with an existing ID
        leaves.setId(1L);
        LeavesDTO leavesDTO = leavesMapper.toDto(leaves);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeavesMockMvc.perform(post("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leavesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get all the leavesList
        restLeavesMockMvc.perform(get("/api/leaves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaves.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS.toString())));
    }

    @Test
    @Transactional
    public void getLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);

        // Get the leaves
        restLeavesMockMvc.perform(get("/api/leaves/{id}", leaves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leaves.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeaves() throws Exception {
        // Get the leaves
        restLeavesMockMvc.perform(get("/api/leaves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();

        // Update the leaves
        Leaves updatedLeaves = leavesRepository.findOne(leaves.getId());
        // Disconnect from session so that the updates on updatedLeaves are not directly saved in db
        em.detach(updatedLeaves);
        updatedLeaves
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .noOfDays(UPDATED_NO_OF_DAYS);
        LeavesDTO leavesDTO = leavesMapper.toDto(updatedLeaves);

        restLeavesMockMvc.perform(put("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leavesDTO)))
            .andExpect(status().isOk());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate);
        Leaves testLeaves = leavesList.get(leavesList.size() - 1);
        assertThat(testLeaves.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaves.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeaves.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaves() throws Exception {
        int databaseSizeBeforeUpdate = leavesRepository.findAll().size();

        // Create the Leaves
        LeavesDTO leavesDTO = leavesMapper.toDto(leaves);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeavesMockMvc.perform(put("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leavesDTO)))
            .andExpect(status().isCreated());

        // Validate the Leaves in the database
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeaves() throws Exception {
        // Initialize the database
        leavesRepository.saveAndFlush(leaves);
        int databaseSizeBeforeDelete = leavesRepository.findAll().size();

        // Get the leaves
        restLeavesMockMvc.perform(delete("/api/leaves/{id}", leaves.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Leaves> leavesList = leavesRepository.findAll();
        assertThat(leavesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leaves.class);
        Leaves leaves1 = new Leaves();
        leaves1.setId(1L);
        Leaves leaves2 = new Leaves();
        leaves2.setId(leaves1.getId());
        assertThat(leaves1).isEqualTo(leaves2);
        leaves2.setId(2L);
        assertThat(leaves1).isNotEqualTo(leaves2);
        leaves1.setId(null);
        assertThat(leaves1).isNotEqualTo(leaves2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeavesDTO.class);
        LeavesDTO leavesDTO1 = new LeavesDTO();
        leavesDTO1.setId(1L);
        LeavesDTO leavesDTO2 = new LeavesDTO();
        assertThat(leavesDTO1).isNotEqualTo(leavesDTO2);
        leavesDTO2.setId(leavesDTO1.getId());
        assertThat(leavesDTO1).isEqualTo(leavesDTO2);
        leavesDTO2.setId(2L);
        assertThat(leavesDTO1).isNotEqualTo(leavesDTO2);
        leavesDTO1.setId(null);
        assertThat(leavesDTO1).isNotEqualTo(leavesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leavesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leavesMapper.fromId(null)).isNull();
    }
}
