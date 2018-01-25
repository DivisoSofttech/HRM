package com.diviso.newhrm.web.rest;

import com.diviso.newhrm.NewhrmApp;

import com.diviso.newhrm.domain.Peoples;
import com.diviso.newhrm.repository.PeoplesRepository;
import com.diviso.newhrm.service.PeoplesService;
import com.diviso.newhrm.service.dto.PeoplesDTO;
import com.diviso.newhrm.service.mapper.PeoplesMapper;
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
 * Test class for the PeoplesResource REST controller.
 *
 * @see PeoplesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewhrmApp.class)
public class PeoplesResourceIntTest {

    private static final Long DEFAULT_REFERENCE = 1L;
    private static final Long UPDATED_REFERENCE = 2L;

    @Autowired
    private PeoplesRepository peoplesRepository;

    @Autowired
    private PeoplesMapper peoplesMapper;

    @Autowired
    private PeoplesService peoplesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeoplesMockMvc;

    private Peoples peoples;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeoplesResource peoplesResource = new PeoplesResource(peoplesService);
        this.restPeoplesMockMvc = MockMvcBuilders.standaloneSetup(peoplesResource)
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
    public static Peoples createEntity(EntityManager em) {
        Peoples peoples = new Peoples()
            .reference(DEFAULT_REFERENCE);
        return peoples;
    }

    @Before
    public void initTest() {
        peoples = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeoples() throws Exception {
        int databaseSizeBeforeCreate = peoplesRepository.findAll().size();

        // Create the Peoples
        PeoplesDTO peoplesDTO = peoplesMapper.toDto(peoples);
        restPeoplesMockMvc.perform(post("/api/peoples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peoplesDTO)))
            .andExpect(status().isCreated());

        // Validate the Peoples in the database
        List<Peoples> peoplesList = peoplesRepository.findAll();
        assertThat(peoplesList).hasSize(databaseSizeBeforeCreate + 1);
        Peoples testPeoples = peoplesList.get(peoplesList.size() - 1);
        assertThat(testPeoples.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createPeoplesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peoplesRepository.findAll().size();

        // Create the Peoples with an existing ID
        peoples.setId(1L);
        PeoplesDTO peoplesDTO = peoplesMapper.toDto(peoples);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeoplesMockMvc.perform(post("/api/peoples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peoplesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Peoples in the database
        List<Peoples> peoplesList = peoplesRepository.findAll();
        assertThat(peoplesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeoples() throws Exception {
        // Initialize the database
        peoplesRepository.saveAndFlush(peoples);

        // Get all the peoplesList
        restPeoplesMockMvc.perform(get("/api/peoples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peoples.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.intValue())));
    }

    @Test
    @Transactional
    public void getPeoples() throws Exception {
        // Initialize the database
        peoplesRepository.saveAndFlush(peoples);

        // Get the peoples
        restPeoplesMockMvc.perform(get("/api/peoples/{id}", peoples.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(peoples.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPeoples() throws Exception {
        // Get the peoples
        restPeoplesMockMvc.perform(get("/api/peoples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeoples() throws Exception {
        // Initialize the database
        peoplesRepository.saveAndFlush(peoples);
        int databaseSizeBeforeUpdate = peoplesRepository.findAll().size();

        // Update the peoples
        Peoples updatedPeoples = peoplesRepository.findOne(peoples.getId());
        // Disconnect from session so that the updates on updatedPeoples are not directly saved in db
        em.detach(updatedPeoples);
        updatedPeoples
            .reference(UPDATED_REFERENCE);
        PeoplesDTO peoplesDTO = peoplesMapper.toDto(updatedPeoples);

        restPeoplesMockMvc.perform(put("/api/peoples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peoplesDTO)))
            .andExpect(status().isOk());

        // Validate the Peoples in the database
        List<Peoples> peoplesList = peoplesRepository.findAll();
        assertThat(peoplesList).hasSize(databaseSizeBeforeUpdate);
        Peoples testPeoples = peoplesList.get(peoplesList.size() - 1);
        assertThat(testPeoples.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingPeoples() throws Exception {
        int databaseSizeBeforeUpdate = peoplesRepository.findAll().size();

        // Create the Peoples
        PeoplesDTO peoplesDTO = peoplesMapper.toDto(peoples);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeoplesMockMvc.perform(put("/api/peoples")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peoplesDTO)))
            .andExpect(status().isCreated());

        // Validate the Peoples in the database
        List<Peoples> peoplesList = peoplesRepository.findAll();
        assertThat(peoplesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeoples() throws Exception {
        // Initialize the database
        peoplesRepository.saveAndFlush(peoples);
        int databaseSizeBeforeDelete = peoplesRepository.findAll().size();

        // Get the peoples
        restPeoplesMockMvc.perform(delete("/api/peoples/{id}", peoples.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Peoples> peoplesList = peoplesRepository.findAll();
        assertThat(peoplesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Peoples.class);
        Peoples peoples1 = new Peoples();
        peoples1.setId(1L);
        Peoples peoples2 = new Peoples();
        peoples2.setId(peoples1.getId());
        assertThat(peoples1).isEqualTo(peoples2);
        peoples2.setId(2L);
        assertThat(peoples1).isNotEqualTo(peoples2);
        peoples1.setId(null);
        assertThat(peoples1).isNotEqualTo(peoples2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeoplesDTO.class);
        PeoplesDTO peoplesDTO1 = new PeoplesDTO();
        peoplesDTO1.setId(1L);
        PeoplesDTO peoplesDTO2 = new PeoplesDTO();
        assertThat(peoplesDTO1).isNotEqualTo(peoplesDTO2);
        peoplesDTO2.setId(peoplesDTO1.getId());
        assertThat(peoplesDTO1).isEqualTo(peoplesDTO2);
        peoplesDTO2.setId(2L);
        assertThat(peoplesDTO1).isNotEqualTo(peoplesDTO2);
        peoplesDTO1.setId(null);
        assertThat(peoplesDTO1).isNotEqualTo(peoplesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(peoplesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(peoplesMapper.fromId(null)).isNull();
    }
}
