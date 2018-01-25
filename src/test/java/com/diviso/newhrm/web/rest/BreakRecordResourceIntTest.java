package com.diviso.newhrm.web.rest;

import com.diviso.newhrm.NewhrmApp;

import com.diviso.newhrm.domain.BreakRecord;
import com.diviso.newhrm.repository.BreakRecordRepository;
import com.diviso.newhrm.service.BreakRecordService;
import com.diviso.newhrm.service.dto.BreakRecordDTO;
import com.diviso.newhrm.service.mapper.BreakRecordMapper;
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
 * Test class for the BreakRecordResource REST controller.
 *
 * @see BreakRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewhrmApp.class)
public class BreakRecordResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BreakRecordRepository breakRecordRepository;

    @Autowired
    private BreakRecordMapper breakRecordMapper;

    @Autowired
    private BreakRecordService breakRecordService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBreakRecordMockMvc;

    private BreakRecord breakRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreakRecordResource breakRecordResource = new BreakRecordResource(breakRecordService);
        this.restBreakRecordMockMvc = MockMvcBuilders.standaloneSetup(breakRecordResource)
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
    public static BreakRecord createEntity(EntityManager em) {
        BreakRecord breakRecord = new BreakRecord()
            .date(DEFAULT_DATE)
            .time(DEFAULT_TIME);
        return breakRecord;
    }

    @Before
    public void initTest() {
        breakRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createBreakRecord() throws Exception {
        int databaseSizeBeforeCreate = breakRecordRepository.findAll().size();

        // Create the BreakRecord
        BreakRecordDTO breakRecordDTO = breakRecordMapper.toDto(breakRecord);
        restBreakRecordMockMvc.perform(post("/api/break-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the BreakRecord in the database
        List<BreakRecord> breakRecordList = breakRecordRepository.findAll();
        assertThat(breakRecordList).hasSize(databaseSizeBeforeCreate + 1);
        BreakRecord testBreakRecord = breakRecordList.get(breakRecordList.size() - 1);
        assertThat(testBreakRecord.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBreakRecord.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createBreakRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breakRecordRepository.findAll().size();

        // Create the BreakRecord with an existing ID
        breakRecord.setId(1L);
        BreakRecordDTO breakRecordDTO = breakRecordMapper.toDto(breakRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreakRecordMockMvc.perform(post("/api/break-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BreakRecord in the database
        List<BreakRecord> breakRecordList = breakRecordRepository.findAll();
        assertThat(breakRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBreakRecords() throws Exception {
        // Initialize the database
        breakRecordRepository.saveAndFlush(breakRecord);

        // Get all the breakRecordList
        restBreakRecordMockMvc.perform(get("/api/break-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breakRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }

    @Test
    @Transactional
    public void getBreakRecord() throws Exception {
        // Initialize the database
        breakRecordRepository.saveAndFlush(breakRecord);

        // Get the breakRecord
        restBreakRecordMockMvc.perform(get("/api/break-records/{id}", breakRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(breakRecord.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBreakRecord() throws Exception {
        // Get the breakRecord
        restBreakRecordMockMvc.perform(get("/api/break-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBreakRecord() throws Exception {
        // Initialize the database
        breakRecordRepository.saveAndFlush(breakRecord);
        int databaseSizeBeforeUpdate = breakRecordRepository.findAll().size();

        // Update the breakRecord
        BreakRecord updatedBreakRecord = breakRecordRepository.findOne(breakRecord.getId());
        // Disconnect from session so that the updates on updatedBreakRecord are not directly saved in db
        em.detach(updatedBreakRecord);
        updatedBreakRecord
            .date(UPDATED_DATE)
            .time(UPDATED_TIME);
        BreakRecordDTO breakRecordDTO = breakRecordMapper.toDto(updatedBreakRecord);

        restBreakRecordMockMvc.perform(put("/api/break-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakRecordDTO)))
            .andExpect(status().isOk());

        // Validate the BreakRecord in the database
        List<BreakRecord> breakRecordList = breakRecordRepository.findAll();
        assertThat(breakRecordList).hasSize(databaseSizeBeforeUpdate);
        BreakRecord testBreakRecord = breakRecordList.get(breakRecordList.size() - 1);
        assertThat(testBreakRecord.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBreakRecord.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBreakRecord() throws Exception {
        int databaseSizeBeforeUpdate = breakRecordRepository.findAll().size();

        // Create the BreakRecord
        BreakRecordDTO breakRecordDTO = breakRecordMapper.toDto(breakRecord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBreakRecordMockMvc.perform(put("/api/break-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breakRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the BreakRecord in the database
        List<BreakRecord> breakRecordList = breakRecordRepository.findAll();
        assertThat(breakRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBreakRecord() throws Exception {
        // Initialize the database
        breakRecordRepository.saveAndFlush(breakRecord);
        int databaseSizeBeforeDelete = breakRecordRepository.findAll().size();

        // Get the breakRecord
        restBreakRecordMockMvc.perform(delete("/api/break-records/{id}", breakRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BreakRecord> breakRecordList = breakRecordRepository.findAll();
        assertThat(breakRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreakRecord.class);
        BreakRecord breakRecord1 = new BreakRecord();
        breakRecord1.setId(1L);
        BreakRecord breakRecord2 = new BreakRecord();
        breakRecord2.setId(breakRecord1.getId());
        assertThat(breakRecord1).isEqualTo(breakRecord2);
        breakRecord2.setId(2L);
        assertThat(breakRecord1).isNotEqualTo(breakRecord2);
        breakRecord1.setId(null);
        assertThat(breakRecord1).isNotEqualTo(breakRecord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreakRecordDTO.class);
        BreakRecordDTO breakRecordDTO1 = new BreakRecordDTO();
        breakRecordDTO1.setId(1L);
        BreakRecordDTO breakRecordDTO2 = new BreakRecordDTO();
        assertThat(breakRecordDTO1).isNotEqualTo(breakRecordDTO2);
        breakRecordDTO2.setId(breakRecordDTO1.getId());
        assertThat(breakRecordDTO1).isEqualTo(breakRecordDTO2);
        breakRecordDTO2.setId(2L);
        assertThat(breakRecordDTO1).isNotEqualTo(breakRecordDTO2);
        breakRecordDTO1.setId(null);
        assertThat(breakRecordDTO1).isNotEqualTo(breakRecordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(breakRecordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(breakRecordMapper.fromId(null)).isNull();
    }
}
