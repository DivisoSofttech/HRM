package com.diviso.newhrm.web.rest;

import com.diviso.newhrm.NewhrmApp;

import com.diviso.newhrm.domain.Breaks;
import com.diviso.newhrm.repository.BreaksRepository;
import com.diviso.newhrm.service.BreaksService;
import com.diviso.newhrm.service.dto.BreaksDTO;
import com.diviso.newhrm.service.mapper.BreaksMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.diviso.newhrm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BreaksResource REST controller.
 *
 * @see BreaksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewhrmApp.class)
public class BreaksResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TILL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BreaksRepository breaksRepository;

    @Autowired
    private BreaksMapper breaksMapper;

    @Autowired
    private BreaksService breaksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBreaksMockMvc;

    private Breaks breaks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreaksResource breaksResource = new BreaksResource(breaksService);
        this.restBreaksMockMvc = MockMvcBuilders.standaloneSetup(breaksResource)
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
    public static Breaks createEntity(EntityManager em) {
        Breaks breaks = new Breaks()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .from(DEFAULT_FROM)
            .till(DEFAULT_TILL);
        return breaks;
    }

    @Before
    public void initTest() {
        breaks = createEntity(em);
    }

    @Test
    @Transactional
    public void createBreaks() throws Exception {
        int databaseSizeBeforeCreate = breaksRepository.findAll().size();

        // Create the Breaks
        BreaksDTO breaksDTO = breaksMapper.toDto(breaks);
        restBreaksMockMvc.perform(post("/api/breaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breaksDTO)))
            .andExpect(status().isCreated());

        // Validate the Breaks in the database
        List<Breaks> breaksList = breaksRepository.findAll();
        assertThat(breaksList).hasSize(databaseSizeBeforeCreate + 1);
        Breaks testBreaks = breaksList.get(breaksList.size() - 1);
        assertThat(testBreaks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBreaks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBreaks.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testBreaks.getTill()).isEqualTo(DEFAULT_TILL);
    }

    @Test
    @Transactional
    public void createBreaksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breaksRepository.findAll().size();

        // Create the Breaks with an existing ID
        breaks.setId(1L);
        BreaksDTO breaksDTO = breaksMapper.toDto(breaks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreaksMockMvc.perform(post("/api/breaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breaksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Breaks in the database
        List<Breaks> breaksList = breaksRepository.findAll();
        assertThat(breaksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBreaks() throws Exception {
        // Initialize the database
        breaksRepository.saveAndFlush(breaks);

        // Get all the breaksList
        restBreaksMockMvc.perform(get("/api/breaks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breaks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].till").value(hasItem(DEFAULT_TILL.toString())));
    }

    @Test
    @Transactional
    public void getBreaks() throws Exception {
        // Initialize the database
        breaksRepository.saveAndFlush(breaks);

        // Get the breaks
        restBreaksMockMvc.perform(get("/api/breaks/{id}", breaks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(breaks.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.till").value(DEFAULT_TILL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBreaks() throws Exception {
        // Get the breaks
        restBreaksMockMvc.perform(get("/api/breaks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBreaks() throws Exception {
        // Initialize the database
        breaksRepository.saveAndFlush(breaks);
        int databaseSizeBeforeUpdate = breaksRepository.findAll().size();

        // Update the breaks
        Breaks updatedBreaks = breaksRepository.findOne(breaks.getId());
        // Disconnect from session so that the updates on updatedBreaks are not directly saved in db
        em.detach(updatedBreaks);
        updatedBreaks
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .from(UPDATED_FROM)
            .till(UPDATED_TILL);
        BreaksDTO breaksDTO = breaksMapper.toDto(updatedBreaks);

        restBreaksMockMvc.perform(put("/api/breaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breaksDTO)))
            .andExpect(status().isOk());

        // Validate the Breaks in the database
        List<Breaks> breaksList = breaksRepository.findAll();
        assertThat(breaksList).hasSize(databaseSizeBeforeUpdate);
        Breaks testBreaks = breaksList.get(breaksList.size() - 1);
        assertThat(testBreaks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBreaks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBreaks.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testBreaks.getTill()).isEqualTo(UPDATED_TILL);
    }

    @Test
    @Transactional
    public void updateNonExistingBreaks() throws Exception {
        int databaseSizeBeforeUpdate = breaksRepository.findAll().size();

        // Create the Breaks
        BreaksDTO breaksDTO = breaksMapper.toDto(breaks);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBreaksMockMvc.perform(put("/api/breaks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breaksDTO)))
            .andExpect(status().isCreated());

        // Validate the Breaks in the database
        List<Breaks> breaksList = breaksRepository.findAll();
        assertThat(breaksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBreaks() throws Exception {
        // Initialize the database
        breaksRepository.saveAndFlush(breaks);
        int databaseSizeBeforeDelete = breaksRepository.findAll().size();

        // Get the breaks
        restBreaksMockMvc.perform(delete("/api/breaks/{id}", breaks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Breaks> breaksList = breaksRepository.findAll();
        assertThat(breaksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Breaks.class);
        Breaks breaks1 = new Breaks();
        breaks1.setId(1L);
        Breaks breaks2 = new Breaks();
        breaks2.setId(breaks1.getId());
        assertThat(breaks1).isEqualTo(breaks2);
        breaks2.setId(2L);
        assertThat(breaks1).isNotEqualTo(breaks2);
        breaks1.setId(null);
        assertThat(breaks1).isNotEqualTo(breaks2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreaksDTO.class);
        BreaksDTO breaksDTO1 = new BreaksDTO();
        breaksDTO1.setId(1L);
        BreaksDTO breaksDTO2 = new BreaksDTO();
        assertThat(breaksDTO1).isNotEqualTo(breaksDTO2);
        breaksDTO2.setId(breaksDTO1.getId());
        assertThat(breaksDTO1).isEqualTo(breaksDTO2);
        breaksDTO2.setId(2L);
        assertThat(breaksDTO1).isNotEqualTo(breaksDTO2);
        breaksDTO1.setId(null);
        assertThat(breaksDTO1).isNotEqualTo(breaksDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(breaksMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(breaksMapper.fromId(null)).isNull();
    }
}
