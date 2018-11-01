package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.PayPartnerLog;
import vpay.homtech.vn.repository.PayPartnerLogRepository;
import vpay.homtech.vn.repository.search.PayPartnerLogSearchRepository;
import vpay.homtech.vn.web.rest.errors.ExceptionTranslator;

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
import java.util.Collections;
import java.util.List;


import static vpay.homtech.vn.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PayPartnerLogResource REST controller.
 *
 * @see PayPartnerLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class PayPartnerLogResourceIntTest {

    @Autowired
    private PayPartnerLogRepository payPartnerLogRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.PayPartnerLogSearchRepositoryMockConfiguration
     */
    @Autowired
    private PayPartnerLogSearchRepository mockPayPartnerLogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayPartnerLogMockMvc;

    private PayPartnerLog payPartnerLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayPartnerLogResource payPartnerLogResource = new PayPartnerLogResource(payPartnerLogRepository, mockPayPartnerLogSearchRepository);
        this.restPayPartnerLogMockMvc = MockMvcBuilders.standaloneSetup(payPartnerLogResource)
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
    public static PayPartnerLog createEntity(EntityManager em) {
        PayPartnerLog payPartnerLog = new PayPartnerLog();
        return payPartnerLog;
    }

    @Before
    public void initTest() {
        payPartnerLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayPartnerLog() throws Exception {
        int databaseSizeBeforeCreate = payPartnerLogRepository.findAll().size();

        // Create the PayPartnerLog
        restPayPartnerLogMockMvc.perform(post("/api/pay-partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payPartnerLog)))
            .andExpect(status().isCreated());

        // Validate the PayPartnerLog in the database
        List<PayPartnerLog> payPartnerLogList = payPartnerLogRepository.findAll();
        assertThat(payPartnerLogList).hasSize(databaseSizeBeforeCreate + 1);
        PayPartnerLog testPayPartnerLog = payPartnerLogList.get(payPartnerLogList.size() - 1);

        // Validate the PayPartnerLog in Elasticsearch
        verify(mockPayPartnerLogSearchRepository, times(1)).save(testPayPartnerLog);
    }

    @Test
    @Transactional
    public void createPayPartnerLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payPartnerLogRepository.findAll().size();

        // Create the PayPartnerLog with an existing ID
        payPartnerLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayPartnerLogMockMvc.perform(post("/api/pay-partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payPartnerLog)))
            .andExpect(status().isBadRequest());

        // Validate the PayPartnerLog in the database
        List<PayPartnerLog> payPartnerLogList = payPartnerLogRepository.findAll();
        assertThat(payPartnerLogList).hasSize(databaseSizeBeforeCreate);

        // Validate the PayPartnerLog in Elasticsearch
        verify(mockPayPartnerLogSearchRepository, times(0)).save(payPartnerLog);
    }

    @Test
    @Transactional
    public void getAllPayPartnerLogs() throws Exception {
        // Initialize the database
        payPartnerLogRepository.saveAndFlush(payPartnerLog);

        // Get all the payPartnerLogList
        restPayPartnerLogMockMvc.perform(get("/api/pay-partner-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payPartnerLog.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPayPartnerLog() throws Exception {
        // Initialize the database
        payPartnerLogRepository.saveAndFlush(payPartnerLog);

        // Get the payPartnerLog
        restPayPartnerLogMockMvc.perform(get("/api/pay-partner-logs/{id}", payPartnerLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payPartnerLog.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPayPartnerLog() throws Exception {
        // Get the payPartnerLog
        restPayPartnerLogMockMvc.perform(get("/api/pay-partner-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayPartnerLog() throws Exception {
        // Initialize the database
        payPartnerLogRepository.saveAndFlush(payPartnerLog);

        int databaseSizeBeforeUpdate = payPartnerLogRepository.findAll().size();

        // Update the payPartnerLog
        PayPartnerLog updatedPayPartnerLog = payPartnerLogRepository.findById(payPartnerLog.getId()).get();
        // Disconnect from session so that the updates on updatedPayPartnerLog are not directly saved in db
        em.detach(updatedPayPartnerLog);

        restPayPartnerLogMockMvc.perform(put("/api/pay-partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayPartnerLog)))
            .andExpect(status().isOk());

        // Validate the PayPartnerLog in the database
        List<PayPartnerLog> payPartnerLogList = payPartnerLogRepository.findAll();
        assertThat(payPartnerLogList).hasSize(databaseSizeBeforeUpdate);
        PayPartnerLog testPayPartnerLog = payPartnerLogList.get(payPartnerLogList.size() - 1);

        // Validate the PayPartnerLog in Elasticsearch
        verify(mockPayPartnerLogSearchRepository, times(1)).save(testPayPartnerLog);
    }

    @Test
    @Transactional
    public void updateNonExistingPayPartnerLog() throws Exception {
        int databaseSizeBeforeUpdate = payPartnerLogRepository.findAll().size();

        // Create the PayPartnerLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayPartnerLogMockMvc.perform(put("/api/pay-partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payPartnerLog)))
            .andExpect(status().isBadRequest());

        // Validate the PayPartnerLog in the database
        List<PayPartnerLog> payPartnerLogList = payPartnerLogRepository.findAll();
        assertThat(payPartnerLogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PayPartnerLog in Elasticsearch
        verify(mockPayPartnerLogSearchRepository, times(0)).save(payPartnerLog);
    }

    @Test
    @Transactional
    public void deletePayPartnerLog() throws Exception {
        // Initialize the database
        payPartnerLogRepository.saveAndFlush(payPartnerLog);

        int databaseSizeBeforeDelete = payPartnerLogRepository.findAll().size();

        // Get the payPartnerLog
        restPayPartnerLogMockMvc.perform(delete("/api/pay-partner-logs/{id}", payPartnerLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayPartnerLog> payPartnerLogList = payPartnerLogRepository.findAll();
        assertThat(payPartnerLogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PayPartnerLog in Elasticsearch
        verify(mockPayPartnerLogSearchRepository, times(1)).deleteById(payPartnerLog.getId());
    }

    @Test
    @Transactional
    public void searchPayPartnerLog() throws Exception {
        // Initialize the database
        payPartnerLogRepository.saveAndFlush(payPartnerLog);
        when(mockPayPartnerLogSearchRepository.search(queryStringQuery("id:" + payPartnerLog.getId())))
            .thenReturn(Collections.singletonList(payPartnerLog));
        // Search the payPartnerLog
        restPayPartnerLogMockMvc.perform(get("/api/_search/pay-partner-logs?query=id:" + payPartnerLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payPartnerLog.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayPartnerLog.class);
        PayPartnerLog payPartnerLog1 = new PayPartnerLog();
        payPartnerLog1.setId(1L);
        PayPartnerLog payPartnerLog2 = new PayPartnerLog();
        payPartnerLog2.setId(payPartnerLog1.getId());
        assertThat(payPartnerLog1).isEqualTo(payPartnerLog2);
        payPartnerLog2.setId(2L);
        assertThat(payPartnerLog1).isNotEqualTo(payPartnerLog2);
        payPartnerLog1.setId(null);
        assertThat(payPartnerLog1).isNotEqualTo(payPartnerLog2);
    }
}
