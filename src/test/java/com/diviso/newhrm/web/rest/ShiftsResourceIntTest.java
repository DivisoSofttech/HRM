package com.diviso.newhrm.web.rest;

import com.diviso.newhrm.NewhrmApp;

import com.diviso.newhrm.domain.Shifts;
import com.diviso.newhrm.repository.ShiftsRepository;
import com.diviso.newhrm.service.ShiftsService;
import com.diviso.newhrm.service.dto.ShiftsDTO;
import com.diviso.newhrm.service.mapper.ShiftsMapper;
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
 * Test class for the ShiftsResource REST controller.
 *
 * @see ShiftsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewhrmApp.class)
public class ShiftsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TILL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ShiftsRepository shiftsRepository;

    @Autowired
    private ShiftsMapper shiftsMapper;

    @Autowired
    private ShiftsService shiftsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShiftsMockMvc;

    private Shifts shifts;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShiftsResource shiftsResource = new ShiftsResource(shiftsService);
        this.restShiftsMockMvc = MockMvcBuilders.standaloneSetup(shiftsResource)
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
    public static Shifts createEntity(EntityManager em) {
        Shifts shifts = new Shifts()
            .name(DEFAULT_NAME)
            .from(DEFAULT_FROM)
            .till(DEFAULT_TILL);
        return shifts;
    }

    @Before
    public void initTest() {
        shifts = createEntity(em);
    }

    @Test
    @Transactional
    public void createShifts() throws Exception {
        int databaseSizeBeforeCreate = shiftsRepository.findAll().size();

        // Create the Shifts
        ShiftsDTO shiftsDTO = shiftsMapper.toDto(shifts);
        restShiftsMockMvc.perform(post("/api/shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiftsDTO)))
            .andExpect(status().isCreated());

        // Validate the Shifts in the database
        List<Shifts> shiftsList = shiftsRepository.findAll();
        assertThat(shiftsList).hasSize(databaseSizeBeforeCreate + 1);
        Shifts testShifts = shiftsList.get(shiftsList.size() - 1);
        assertThat(testShifts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShifts.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testShifts.getTill()).isEqualTo(DEFAULT_TILL);
    }

    @Test
    @Transactional
    public void createShiftsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shiftsRepository.findAll().size();

        // Create the Shifts with an existing ID
        shifts.setId(1L);
        ShiftsDTO shiftsDTO = shiftsMapper.toDto(shifts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShiftsMockMvc.perform(post("/api/shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiftsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shifts in the database
        List<Shifts> shiftsList = shiftsRepository.findAll();
        assertThat(shiftsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShifts() throws Exception {
        // Initialize the database
        shiftsRepository.saveAndFlush(shifts);

        // Get all the shiftsList
        restShiftsMockMvc.perform(get("/api/shifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shifts.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].till").value(hasItem(DEFAULT_TILL.toString())));
    }

    @Test
    @Transactional
    public void getShifts() throws Exception {
        // Initialize the database
        shiftsRepository.saveAndFlush(shifts);

        // Get the shifts
        restShiftsMockMvc.perform(get("/api/shifts/{id}", shifts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shifts.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.till").value(DEFAULT_TILL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShifts() throws Exception {
        // Get the shifts
        restShiftsMockMvc.perform(get("/api/shifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShifts() throws Exception {
        // Initialize the database
        shiftsRepository.saveAndFlush(shifts);
        int databaseSizeBeforeUpdate = shiftsRepository.findAll().size();

        // Update the shifts
        Shifts updatedShifts = shiftsRepository.findOne(shifts.getId());
        // Disconnect from session so that the updates on updatedShifts are not directly saved in db
        em.detach(updatedShifts);
        updatedShifts
            .name(UPDATED_NAME)
            .from(UPDATED_FROM)
            .till(UPDATED_TILL);
        ShiftsDTO shiftsDTO = shiftsMapper.toDto(updatedShifts);

        restShiftsMockMvc.perform(put("/api/shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiftsDTO)))
            .andExpect(status().isOk());

        // Validate the Shifts in the database
        List<Shifts> shiftsList = shiftsRepository.findAll();
        assertThat(shiftsList).hasSize(databaseSizeBeforeUpdate);
        Shifts testShifts = shiftsList.get(shiftsList.size() - 1);
        assertThat(testShifts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShifts.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testShifts.getTill()).isEqualTo(UPDATED_TILL);
    }

    @Test
    @Transactional
    public void updateNonExistingShifts() throws Exception {
        int databaseSizeBeforeUpdate = shiftsRepository.findAll().size();

        // Create the Shifts
        ShiftsDTO shiftsDTO = shiftsMapper.toDto(shifts);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShiftsMockMvc.perform(put("/api/shifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiftsDTO)))
            .andExpect(status().isCreated());

        // Validate the Shifts in the database
        List<Shifts> shiftsList = shiftsRepository.findAll();
        assertThat(shiftsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShifts() throws Exception {
        // Initialize the database
        shiftsRepository.saveAndFlush(shifts);
        int databaseSizeBeforeDelete = shiftsRepository.findAll().size();

        // Get the shifts
        restShiftsMockMvc.perform(delete("/api/shifts/{id}", shifts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shifts> shiftsList = shiftsRepository.findAll();
        assertThat(shiftsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shifts.class);
        Shifts shifts1 = new Shifts();
        shifts1.setId(1L);
        Shifts shifts2 = new Shifts();
        shifts2.setId(shifts1.getId());
        assertThat(shifts1).isEqualTo(shifts2);
        shifts2.setId(2L);
        assertThat(shifts1).isNotEqualTo(shifts2);
        shifts1.setId(null);
        assertThat(shifts1).isNotEqualTo(shifts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShiftsDTO.class);
        ShiftsDTO shiftsDTO1 = new ShiftsDTO();
        shiftsDTO1.setId(1L);
        ShiftsDTO shiftsDTO2 = new ShiftsDTO();
        assertThat(shiftsDTO1).isNotEqualTo(shiftsDTO2);
        shiftsDTO2.setId(shiftsDTO1.getId());
        assertThat(shiftsDTO1).isEqualTo(shiftsDTO2);
        shiftsDTO2.setId(2L);
        assertThat(shiftsDTO1).isNotEqualTo(shiftsDTO2);
        shiftsDTO1.setId(null);
        assertThat(shiftsDTO1).isNotEqualTo(shiftsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shiftsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shiftsMapper.fromId(null)).isNull();
    }
}
