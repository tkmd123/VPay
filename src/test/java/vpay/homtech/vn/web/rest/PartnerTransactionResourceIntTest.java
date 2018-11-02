package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.PartnerTransaction;
import vpay.homtech.vn.repository.PartnerTransactionRepository;
import vpay.homtech.vn.repository.search.PartnerTransactionSearchRepository;
import vpay.homtech.vn.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the PartnerTransactionResource REST controller.
 *
 * @see PartnerTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class PartnerTransactionResourceIntTest {

    private static final Long DEFAULT_PARTNER_TRANS_AMOUNT = 1L;
    private static final Long UPDATED_PARTNER_TRANS_AMOUNT = 2L;

    private static final Instant DEFAULT_PARTNER_TRANS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PARTNER_TRANS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PARTNER_TRANS_REF = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TRANS_REF = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TRANS_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TRANS_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TRANS_UDF_1 = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TRANS_UDF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TRANS_UDF_2 = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TRANS_UDF_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TRANS_UDF_3 = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TRANS_UDF_3 = "BBBBBBBBBB";

    @Autowired
    private PartnerTransactionRepository partnerTransactionRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.PartnerTransactionSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartnerTransactionSearchRepository mockPartnerTransactionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartnerTransactionMockMvc;

    private PartnerTransaction partnerTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerTransactionResource partnerTransactionResource = new PartnerTransactionResource(partnerTransactionRepository, mockPartnerTransactionSearchRepository);
        this.restPartnerTransactionMockMvc = MockMvcBuilders.standaloneSetup(partnerTransactionResource)
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
    public static PartnerTransaction createEntity(EntityManager em) {
        PartnerTransaction partnerTransaction = new PartnerTransaction()
            .partnerTransAmount(DEFAULT_PARTNER_TRANS_AMOUNT)
            .partnerTransDate(DEFAULT_PARTNER_TRANS_DATE)
            .partnerTransRef(DEFAULT_PARTNER_TRANS_REF)
            .partnerTransUsername(DEFAULT_PARTNER_TRANS_USERNAME)
            .partnerTransUDF1(DEFAULT_PARTNER_TRANS_UDF_1)
            .partnerTransUDF2(DEFAULT_PARTNER_TRANS_UDF_2)
            .partnerTransUDF3(DEFAULT_PARTNER_TRANS_UDF_3);
        return partnerTransaction;
    }

    @Before
    public void initTest() {
        partnerTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnerTransaction() throws Exception {
        int databaseSizeBeforeCreate = partnerTransactionRepository.findAll().size();

        // Create the PartnerTransaction
        restPartnerTransactionMockMvc.perform(post("/api/partner-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTransaction)))
            .andExpect(status().isCreated());

        // Validate the PartnerTransaction in the database
        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerTransaction testPartnerTransaction = partnerTransactionList.get(partnerTransactionList.size() - 1);
        assertThat(testPartnerTransaction.getPartnerTransAmount()).isEqualTo(DEFAULT_PARTNER_TRANS_AMOUNT);
        assertThat(testPartnerTransaction.getPartnerTransDate()).isEqualTo(DEFAULT_PARTNER_TRANS_DATE);
        assertThat(testPartnerTransaction.getPartnerTransRef()).isEqualTo(DEFAULT_PARTNER_TRANS_REF);
        assertThat(testPartnerTransaction.getPartnerTransUsername()).isEqualTo(DEFAULT_PARTNER_TRANS_USERNAME);
        assertThat(testPartnerTransaction.getPartnerTransUDF1()).isEqualTo(DEFAULT_PARTNER_TRANS_UDF_1);
        assertThat(testPartnerTransaction.getPartnerTransUDF2()).isEqualTo(DEFAULT_PARTNER_TRANS_UDF_2);
        assertThat(testPartnerTransaction.getPartnerTransUDF3()).isEqualTo(DEFAULT_PARTNER_TRANS_UDF_3);

        // Validate the PartnerTransaction in Elasticsearch
        verify(mockPartnerTransactionSearchRepository, times(1)).save(testPartnerTransaction);
    }

    @Test
    @Transactional
    public void createPartnerTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerTransactionRepository.findAll().size();

        // Create the PartnerTransaction with an existing ID
        partnerTransaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerTransactionMockMvc.perform(post("/api/partner-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerTransaction in the database
        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeCreate);

        // Validate the PartnerTransaction in Elasticsearch
        verify(mockPartnerTransactionSearchRepository, times(0)).save(partnerTransaction);
    }

    @Test
    @Transactional
    public void checkPartnerTransAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerTransactionRepository.findAll().size();
        // set the field null
        partnerTransaction.setPartnerTransAmount(null);

        // Create the PartnerTransaction, which fails.

        restPartnerTransactionMockMvc.perform(post("/api/partner-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTransaction)))
            .andExpect(status().isBadRequest());

        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartnerTransDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerTransactionRepository.findAll().size();
        // set the field null
        partnerTransaction.setPartnerTransDate(null);

        // Create the PartnerTransaction, which fails.

        restPartnerTransactionMockMvc.perform(post("/api/partner-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTransaction)))
            .andExpect(status().isBadRequest());

        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartnerTransactions() throws Exception {
        // Initialize the database
        partnerTransactionRepository.saveAndFlush(partnerTransaction);

        // Get all the partnerTransactionList
        restPartnerTransactionMockMvc.perform(get("/api/partner-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerTransAmount").value(hasItem(DEFAULT_PARTNER_TRANS_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].partnerTransDate").value(hasItem(DEFAULT_PARTNER_TRANS_DATE.toString())))
            .andExpect(jsonPath("$.[*].partnerTransRef").value(hasItem(DEFAULT_PARTNER_TRANS_REF.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUsername").value(hasItem(DEFAULT_PARTNER_TRANS_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUDF1").value(hasItem(DEFAULT_PARTNER_TRANS_UDF_1.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUDF2").value(hasItem(DEFAULT_PARTNER_TRANS_UDF_2.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUDF3").value(hasItem(DEFAULT_PARTNER_TRANS_UDF_3.toString())));
    }
    
    @Test
    @Transactional
    public void getPartnerTransaction() throws Exception {
        // Initialize the database
        partnerTransactionRepository.saveAndFlush(partnerTransaction);

        // Get the partnerTransaction
        restPartnerTransactionMockMvc.perform(get("/api/partner-transactions/{id}", partnerTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerTransaction.getId().intValue()))
            .andExpect(jsonPath("$.partnerTransAmount").value(DEFAULT_PARTNER_TRANS_AMOUNT.intValue()))
            .andExpect(jsonPath("$.partnerTransDate").value(DEFAULT_PARTNER_TRANS_DATE.toString()))
            .andExpect(jsonPath("$.partnerTransRef").value(DEFAULT_PARTNER_TRANS_REF.toString()))
            .andExpect(jsonPath("$.partnerTransUsername").value(DEFAULT_PARTNER_TRANS_USERNAME.toString()))
            .andExpect(jsonPath("$.partnerTransUDF1").value(DEFAULT_PARTNER_TRANS_UDF_1.toString()))
            .andExpect(jsonPath("$.partnerTransUDF2").value(DEFAULT_PARTNER_TRANS_UDF_2.toString()))
            .andExpect(jsonPath("$.partnerTransUDF3").value(DEFAULT_PARTNER_TRANS_UDF_3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartnerTransaction() throws Exception {
        // Get the partnerTransaction
        restPartnerTransactionMockMvc.perform(get("/api/partner-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerTransaction() throws Exception {
        // Initialize the database
        partnerTransactionRepository.saveAndFlush(partnerTransaction);

        int databaseSizeBeforeUpdate = partnerTransactionRepository.findAll().size();

        // Update the partnerTransaction
        PartnerTransaction updatedPartnerTransaction = partnerTransactionRepository.findById(partnerTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerTransaction are not directly saved in db
        em.detach(updatedPartnerTransaction);
        updatedPartnerTransaction
            .partnerTransAmount(UPDATED_PARTNER_TRANS_AMOUNT)
            .partnerTransDate(UPDATED_PARTNER_TRANS_DATE)
            .partnerTransRef(UPDATED_PARTNER_TRANS_REF)
            .partnerTransUsername(UPDATED_PARTNER_TRANS_USERNAME)
            .partnerTransUDF1(UPDATED_PARTNER_TRANS_UDF_1)
            .partnerTransUDF2(UPDATED_PARTNER_TRANS_UDF_2)
            .partnerTransUDF3(UPDATED_PARTNER_TRANS_UDF_3);

        restPartnerTransactionMockMvc.perform(put("/api/partner-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerTransaction)))
            .andExpect(status().isOk());

        // Validate the PartnerTransaction in the database
        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeUpdate);
        PartnerTransaction testPartnerTransaction = partnerTransactionList.get(partnerTransactionList.size() - 1);
        assertThat(testPartnerTransaction.getPartnerTransAmount()).isEqualTo(UPDATED_PARTNER_TRANS_AMOUNT);
        assertThat(testPartnerTransaction.getPartnerTransDate()).isEqualTo(UPDATED_PARTNER_TRANS_DATE);
        assertThat(testPartnerTransaction.getPartnerTransRef()).isEqualTo(UPDATED_PARTNER_TRANS_REF);
        assertThat(testPartnerTransaction.getPartnerTransUsername()).isEqualTo(UPDATED_PARTNER_TRANS_USERNAME);
        assertThat(testPartnerTransaction.getPartnerTransUDF1()).isEqualTo(UPDATED_PARTNER_TRANS_UDF_1);
        assertThat(testPartnerTransaction.getPartnerTransUDF2()).isEqualTo(UPDATED_PARTNER_TRANS_UDF_2);
        assertThat(testPartnerTransaction.getPartnerTransUDF3()).isEqualTo(UPDATED_PARTNER_TRANS_UDF_3);

        // Validate the PartnerTransaction in Elasticsearch
        verify(mockPartnerTransactionSearchRepository, times(1)).save(testPartnerTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnerTransaction() throws Exception {
        int databaseSizeBeforeUpdate = partnerTransactionRepository.findAll().size();

        // Create the PartnerTransaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerTransactionMockMvc.perform(put("/api/partner-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTransaction)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerTransaction in the database
        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PartnerTransaction in Elasticsearch
        verify(mockPartnerTransactionSearchRepository, times(0)).save(partnerTransaction);
    }

    @Test
    @Transactional
    public void deletePartnerTransaction() throws Exception {
        // Initialize the database
        partnerTransactionRepository.saveAndFlush(partnerTransaction);

        int databaseSizeBeforeDelete = partnerTransactionRepository.findAll().size();

        // Get the partnerTransaction
        restPartnerTransactionMockMvc.perform(delete("/api/partner-transactions/{id}", partnerTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerTransaction> partnerTransactionList = partnerTransactionRepository.findAll();
        assertThat(partnerTransactionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PartnerTransaction in Elasticsearch
        verify(mockPartnerTransactionSearchRepository, times(1)).deleteById(partnerTransaction.getId());
    }

    @Test
    @Transactional
    public void searchPartnerTransaction() throws Exception {
        // Initialize the database
        partnerTransactionRepository.saveAndFlush(partnerTransaction);
        when(mockPartnerTransactionSearchRepository.search(queryStringQuery("id:" + partnerTransaction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(partnerTransaction), PageRequest.of(0, 1), 1));
        // Search the partnerTransaction
        restPartnerTransactionMockMvc.perform(get("/api/_search/partner-transactions?query=id:" + partnerTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerTransAmount").value(hasItem(DEFAULT_PARTNER_TRANS_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].partnerTransDate").value(hasItem(DEFAULT_PARTNER_TRANS_DATE.toString())))
            .andExpect(jsonPath("$.[*].partnerTransRef").value(hasItem(DEFAULT_PARTNER_TRANS_REF.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUsername").value(hasItem(DEFAULT_PARTNER_TRANS_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUDF1").value(hasItem(DEFAULT_PARTNER_TRANS_UDF_1.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUDF2").value(hasItem(DEFAULT_PARTNER_TRANS_UDF_2.toString())))
            .andExpect(jsonPath("$.[*].partnerTransUDF3").value(hasItem(DEFAULT_PARTNER_TRANS_UDF_3.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerTransaction.class);
        PartnerTransaction partnerTransaction1 = new PartnerTransaction();
        partnerTransaction1.setId(1L);
        PartnerTransaction partnerTransaction2 = new PartnerTransaction();
        partnerTransaction2.setId(partnerTransaction1.getId());
        assertThat(partnerTransaction1).isEqualTo(partnerTransaction2);
        partnerTransaction2.setId(2L);
        assertThat(partnerTransaction1).isNotEqualTo(partnerTransaction2);
        partnerTransaction1.setId(null);
        assertThat(partnerTransaction1).isNotEqualTo(partnerTransaction2);
    }
}
