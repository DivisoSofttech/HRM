package com.diviso.newhrm.web.rest;

import com.diviso.newhrm.NewhrmApp;

import com.diviso.newhrm.domain.LeaveRecord;
import com.diviso.newhrm.repository.LeaveRecordRepository;
import com.diviso.newhrm.service.LeaveRecordService;
import com.diviso.newhrm.service.dto.LeaveRecordDTO;
import com.diviso.newhrm.service.mapper.LeaveRecordMapper;
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
 * Test class for the LeaveRecordResource REST controller.
 *
 * @see LeaveRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewhrmApp.class)
public class LeaveRecordResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LeaveRecordRepository leaveRecordRepository;

    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Autowired
    private LeaveRecordService leaveRecordService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLeaveRecordMockMvc;

    private LeaveRecord leaveRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveRecordResource leaveRecordResource = new LeaveRecordResource(leaveRecordService);
        this.restLeaveRecordMockMvc = MockMvcBuilders.standaloneSetup(leaveRecordResource)
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
    public static LeaveRecord createEntity(EntityManager em) {
        LeaveRecord leaveRecord = new LeaveRecord()
            .date(DEFAULT_DATE)
            .time(DEFAULT_TIME);
        return leaveRecord;
    }

    @Before
    public void initTest() {
        leaveRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveRecord() throws Exception {
        int databaseSizeBeforeCreate = leaveRecordRepository.findAll().size();

        // Create the LeaveRecord
        LeaveRecordDTO leaveRecordDTO = leaveRecordMapper.toDto(leaveRecord);
        restLeaveRecordMockMvc.perform(post("/api/leave-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveRecord in the database
        List<LeaveRecord> leaveRecordList = leaveRecordRepository.findAll();
        assertThat(leaveRecordList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveRecord testLeaveRecord = leaveRecordList.get(leaveRecordList.size() - 1);
        assertThat(testLeaveRecord.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLeaveRecord.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createLeaveRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveRecordRepository.findAll().size();

        // Create the LeaveRecord with an existing ID
        leaveRecord.setId(1L);
        LeaveRecordDTO leaveRecordDTO = leaveRecordMapper.toDto(leaveRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveRecordMockMvc.perform(post("/api/leave-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveRecord in the database
        List<LeaveRecord> leaveRecordList = leaveRecordRepository.findAll();
        assertThat(leaveRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLeaveRecords() throws Exception {
        // Initialize the database
        leaveRecordRepository.saveAndFlush(leaveRecord);

        // Get all the leaveRecordList
        restLeaveRecordMockMvc.perform(get("/api/leave-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }

    @Test
    @Transactional
    public void getLeaveRecord() throws Exception {
        // Initialize the database
        leaveRecordRepository.saveAndFlush(leaveRecord);

        // Get the leaveRecord
        restLeaveRecordMockMvc.perform(get("/api/leave-records/{id}", leaveRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leaveRecord.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveRecord() throws Exception {
        // Get the leaveRecord
        restLeaveRecordMockMvc.perform(get("/api/leave-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveRecord() throws Exception {
        // Initialize the database
        leaveRecordRepository.saveAndFlush(leaveRecord);
        int databaseSizeBeforeUpdate = leaveRecordRepository.findAll().size();

        // Update the leaveRecord
        LeaveRecord updatedLeaveRecord = leaveRecordRepository.findOne(leaveRecord.getId());
        // Disconnect from session so that the updates on updatedLeaveRecord are not directly saved in db
        em.detach(updatedLeaveRecord);
        updatedLeaveRecord
            .date(UPDATED_DATE)
            .time(UPDATED_TIME);
        LeaveRecordDTO leaveRecordDTO = leaveRecordMapper.toDto(updatedLeaveRecord);

        restLeaveRecordMockMvc.perform(put("/api/leave-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveRecordDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveRecord in the database
        List<LeaveRecord> leaveRecordList = leaveRecordRepository.findAll();
        assertThat(leaveRecordList).hasSize(databaseSizeBeforeUpdate);
        LeaveRecord testLeaveRecord = leaveRecordList.get(leaveRecordList.size() - 1);
        assertThat(testLeaveRecord.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLeaveRecord.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveRecord() throws Exception {
        int databaseSizeBeforeUpdate = leaveRecordRepository.findAll().size();

        // Create the LeaveRecord
        LeaveRecordDTO leaveRecordDTO = leaveRecordMapper.toDto(leaveRecord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLeaveRecordMockMvc.perform(put("/api/leave-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveRecord in the database
        List<LeaveRecord> leaveRecordList = leaveRecordRepository.findAll();
        assertThat(leaveRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLeaveRecord() throws Exception {
        // Initialize the database
        leaveRecordRepository.saveAndFlush(leaveRecord);
        int databaseSizeBeforeDelete = leaveRecordRepository.findAll().size();

        // Get the leaveRecord
        restLeaveRecordMockMvc.perform(delete("/api/leave-records/{id}", leaveRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeaveRecord> leaveRecordList = leaveRecordRepository.findAll();
        assertThat(leaveRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRecord.class);
        LeaveRecord leaveRecord1 = new LeaveRecord();
        leaveRecord1.setId(1L);
        LeaveRecord leaveRecord2 = new LeaveRecord();
        leaveRecord2.setId(leaveRecord1.getId());
        assertThat(leaveRecord1).isEqualTo(leaveRecord2);
        leaveRecord2.setId(2L);
        assertThat(leaveRecord1).isNotEqualTo(leaveRecord2);
        leaveRecord1.setId(null);
        assertThat(leaveRecord1).isNotEqualTo(leaveRecord2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRecordDTO.class);
        LeaveRecordDTO leaveRecordDTO1 = new LeaveRecordDTO();
        leaveRecordDTO1.setId(1L);
        LeaveRecordDTO leaveRecordDTO2 = new LeaveRecordDTO();
        assertThat(leaveRecordDTO1).isNotEqualTo(leaveRecordDTO2);
        leaveRecordDTO2.setId(leaveRecordDTO1.getId());
        assertThat(leaveRecordDTO1).isEqualTo(leaveRecordDTO2);
        leaveRecordDTO2.setId(2L);
        assertThat(leaveRecordDTO1).isNotEqualTo(leaveRecordDTO2);
        leaveRecordDTO1.setId(null);
        assertThat(leaveRecordDTO1).isNotEqualTo(leaveRecordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leaveRecordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leaveRecordMapper.fromId(null)).isNull();
    }
}
