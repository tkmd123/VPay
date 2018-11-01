package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.PartnerLog;
import vpay.homtech.vn.repository.PartnerLogRepository;
import vpay.homtech.vn.repository.search.PartnerLogSearchRepository;
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
 * Test class for the PartnerLogResource REST controller.
 *
 * @see PartnerLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class PartnerLogResourceIntTest {

    @Autowired
    private PartnerLogRepository partnerLogRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.PartnerLogSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartnerLogSearchRepository mockPartnerLogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartnerLogMockMvc;

    private PartnerLog partnerLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerLogResource partnerLogResource = new PartnerLogResource(partnerLogRepository, mockPartnerLogSearchRepository);
        this.restPartnerLogMockMvc = MockMvcBuilders.standaloneSetup(partnerLogResource)
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
    public static PartnerLog createEntity(EntityManager em) {
        PartnerLog partnerLog = new PartnerLog();
        return partnerLog;
    }

    @Before
    public void initTest() {
        partnerLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnerLog() throws Exception {
        int databaseSizeBeforeCreate = partnerLogRepository.findAll().size();

        // Create the PartnerLog
        restPartnerLogMockMvc.perform(post("/api/partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerLog)))
            .andExpect(status().isCreated());

        // Validate the PartnerLog in the database
        List<PartnerLog> partnerLogList = partnerLogRepository.findAll();
        assertThat(partnerLogList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerLog testPartnerLog = partnerLogList.get(partnerLogList.size() - 1);

        // Validate the PartnerLog in Elasticsearch
        verify(mockPartnerLogSearchRepository, times(1)).save(testPartnerLog);
    }

    @Test
    @Transactional
    public void createPartnerLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerLogRepository.findAll().size();

        // Create the PartnerLog with an existing ID
        partnerLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerLogMockMvc.perform(post("/api/partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerLog)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerLog in the database
        List<PartnerLog> partnerLogList = partnerLogRepository.findAll();
        assertThat(partnerLogList).hasSize(databaseSizeBeforeCreate);

        // Validate the PartnerLog in Elasticsearch
        verify(mockPartnerLogSearchRepository, times(0)).save(partnerLog);
    }

    @Test
    @Transactional
    public void getAllPartnerLogs() throws Exception {
        // Initialize the database
        partnerLogRepository.saveAndFlush(partnerLog);

        // Get all the partnerLogList
        restPartnerLogMockMvc.perform(get("/api/partner-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerLog.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPartnerLog() throws Exception {
        // Initialize the database
        partnerLogRepository.saveAndFlush(partnerLog);

        // Get the partnerLog
        restPartnerLogMockMvc.perform(get("/api/partner-logs/{id}", partnerLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerLog.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartnerLog() throws Exception {
        // Get the partnerLog
        restPartnerLogMockMvc.perform(get("/api/partner-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerLog() throws Exception {
        // Initialize the database
        partnerLogRepository.saveAndFlush(partnerLog);

        int databaseSizeBeforeUpdate = partnerLogRepository.findAll().size();

        // Update the partnerLog
        PartnerLog updatedPartnerLog = partnerLogRepository.findById(partnerLog.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerLog are not directly saved in db
        em.detach(updatedPartnerLog);

        restPartnerLogMockMvc.perform(put("/api/partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerLog)))
            .andExpect(status().isOk());

        // Validate the PartnerLog in the database
        List<PartnerLog> partnerLogList = partnerLogRepository.findAll();
        assertThat(partnerLogList).hasSize(databaseSizeBeforeUpdate);
        PartnerLog testPartnerLog = partnerLogList.get(partnerLogList.size() - 1);

        // Validate the PartnerLog in Elasticsearch
        verify(mockPartnerLogSearchRepository, times(1)).save(testPartnerLog);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnerLog() throws Exception {
        int databaseSizeBeforeUpdate = partnerLogRepository.findAll().size();

        // Create the PartnerLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerLogMockMvc.perform(put("/api/partner-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerLog)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerLog in the database
        List<PartnerLog> partnerLogList = partnerLogRepository.findAll();
        assertThat(partnerLogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PartnerLog in Elasticsearch
        verify(mockPartnerLogSearchRepository, times(0)).save(partnerLog);
    }

    @Test
    @Transactional
    public void deletePartnerLog() throws Exception {
        // Initialize the database
        partnerLogRepository.saveAndFlush(partnerLog);

        int databaseSizeBeforeDelete = partnerLogRepository.findAll().size();

        // Get the partnerLog
        restPartnerLogMockMvc.perform(delete("/api/partner-logs/{id}", partnerLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerLog> partnerLogList = partnerLogRepository.findAll();
        assertThat(partnerLogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PartnerLog in Elasticsearch
        verify(mockPartnerLogSearchRepository, times(1)).deleteById(partnerLog.getId());
    }

    @Test
    @Transactional
    public void searchPartnerLog() throws Exception {
        // Initialize the database
        partnerLogRepository.saveAndFlush(partnerLog);
        when(mockPartnerLogSearchRepository.search(queryStringQuery("id:" + partnerLog.getId())))
            .thenReturn(Collections.singletonList(partnerLog));
        // Search the partnerLog
        restPartnerLogMockMvc.perform(get("/api/_search/partner-logs?query=id:" + partnerLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerLog.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerLog.class);
        PartnerLog partnerLog1 = new PartnerLog();
        partnerLog1.setId(1L);
        PartnerLog partnerLog2 = new PartnerLog();
        partnerLog2.setId(partnerLog1.getId());
        assertThat(partnerLog1).isEqualTo(partnerLog2);
        partnerLog2.setId(2L);
        assertThat(partnerLog1).isNotEqualTo(partnerLog2);
        partnerLog1.setId(null);
        assertThat(partnerLog1).isNotEqualTo(partnerLog2);
    }
}
