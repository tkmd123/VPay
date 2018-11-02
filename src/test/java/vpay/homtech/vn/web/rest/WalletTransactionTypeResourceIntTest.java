package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.WalletTransactionType;
import vpay.homtech.vn.repository.WalletTransactionTypeRepository;
import vpay.homtech.vn.repository.search.WalletTransactionTypeSearchRepository;
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
 * Test class for the WalletTransactionTypeResource REST controller.
 *
 * @see WalletTransactionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class WalletTransactionTypeResourceIntTest {

    private static final String DEFAULT_WALLET_TRANS_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TRANS_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TRANS_TYPE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_WALLET_TRANS_TYPE_FLAG = 1;
    private static final Integer UPDATED_WALLET_TRANS_TYPE_FLAG = 2;

    private static final String DEFAULT_WALLET_TRANS_TYPE_UDF_1 = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_UDF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TRANS_TYPE_UDF_2 = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_UDF_2 = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TRANS_TYPE_UDF_3 = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_UDF_3 = "BBBBBBBBBB";

    @Autowired
    private WalletTransactionTypeRepository walletTransactionTypeRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.WalletTransactionTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalletTransactionTypeSearchRepository mockWalletTransactionTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWalletTransactionTypeMockMvc;

    private WalletTransactionType walletTransactionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WalletTransactionTypeResource walletTransactionTypeResource = new WalletTransactionTypeResource(walletTransactionTypeRepository, mockWalletTransactionTypeSearchRepository);
        this.restWalletTransactionTypeMockMvc = MockMvcBuilders.standaloneSetup(walletTransactionTypeResource)
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
    public static WalletTransactionType createEntity(EntityManager em) {
        WalletTransactionType walletTransactionType = new WalletTransactionType()
            .walletTransTypeCode(DEFAULT_WALLET_TRANS_TYPE_CODE)
            .walletTransTypeName(DEFAULT_WALLET_TRANS_TYPE_NAME)
            .walletTransTypeDesc(DEFAULT_WALLET_TRANS_TYPE_DESC)
            .walletTransTypeFlag(DEFAULT_WALLET_TRANS_TYPE_FLAG)
            .walletTransTypeUDF1(DEFAULT_WALLET_TRANS_TYPE_UDF_1)
            .walletTransTypeUDF2(DEFAULT_WALLET_TRANS_TYPE_UDF_2)
            .walletTransTypeUDF3(DEFAULT_WALLET_TRANS_TYPE_UDF_3);
        return walletTransactionType;
    }

    @Before
    public void initTest() {
        walletTransactionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalletTransactionType() throws Exception {
        int databaseSizeBeforeCreate = walletTransactionTypeRepository.findAll().size();

        // Create the WalletTransactionType
        restWalletTransactionTypeMockMvc.perform(post("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransactionType)))
            .andExpect(status().isCreated());

        // Validate the WalletTransactionType in the database
        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WalletTransactionType testWalletTransactionType = walletTransactionTypeList.get(walletTransactionTypeList.size() - 1);
        assertThat(testWalletTransactionType.getWalletTransTypeCode()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_CODE);
        assertThat(testWalletTransactionType.getWalletTransTypeName()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_NAME);
        assertThat(testWalletTransactionType.getWalletTransTypeDesc()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_DESC);
        assertThat(testWalletTransactionType.getWalletTransTypeFlag()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_FLAG);
        assertThat(testWalletTransactionType.getWalletTransTypeUDF1()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_UDF_1);
        assertThat(testWalletTransactionType.getWalletTransTypeUDF2()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_UDF_2);
        assertThat(testWalletTransactionType.getWalletTransTypeUDF3()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_UDF_3);

        // Validate the WalletTransactionType in Elasticsearch
        verify(mockWalletTransactionTypeSearchRepository, times(1)).save(testWalletTransactionType);
    }

    @Test
    @Transactional
    public void createWalletTransactionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletTransactionTypeRepository.findAll().size();

        // Create the WalletTransactionType with an existing ID
        walletTransactionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletTransactionTypeMockMvc.perform(post("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransactionType)))
            .andExpect(status().isBadRequest());

        // Validate the WalletTransactionType in the database
        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the WalletTransactionType in Elasticsearch
        verify(mockWalletTransactionTypeSearchRepository, times(0)).save(walletTransactionType);
    }

    @Test
    @Transactional
    public void checkWalletTransTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletTransactionTypeRepository.findAll().size();
        // set the field null
        walletTransactionType.setWalletTransTypeCode(null);

        // Create the WalletTransactionType, which fails.

        restWalletTransactionTypeMockMvc.perform(post("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransactionType)))
            .andExpect(status().isBadRequest());

        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWalletTransTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletTransactionTypeRepository.findAll().size();
        // set the field null
        walletTransactionType.setWalletTransTypeName(null);

        // Create the WalletTransactionType, which fails.

        restWalletTransactionTypeMockMvc.perform(post("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransactionType)))
            .andExpect(status().isBadRequest());

        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWalletTransTypeFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletTransactionTypeRepository.findAll().size();
        // set the field null
        walletTransactionType.setWalletTransTypeFlag(null);

        // Create the WalletTransactionType, which fails.

        restWalletTransactionTypeMockMvc.perform(post("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransactionType)))
            .andExpect(status().isBadRequest());

        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWalletTransactionTypes() throws Exception {
        // Initialize the database
        walletTransactionTypeRepository.saveAndFlush(walletTransactionType);

        // Get all the walletTransactionTypeList
        restWalletTransactionTypeMockMvc.perform(get("/api/wallet-transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletTransactionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletTransTypeCode").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeName").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeDesc").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_DESC.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeFlag").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_FLAG)))
            .andExpect(jsonPath("$.[*].walletTransTypeUDF1").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_UDF_1.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeUDF2").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_UDF_2.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeUDF3").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_UDF_3.toString())));
    }
    
    @Test
    @Transactional
    public void getWalletTransactionType() throws Exception {
        // Initialize the database
        walletTransactionTypeRepository.saveAndFlush(walletTransactionType);

        // Get the walletTransactionType
        restWalletTransactionTypeMockMvc.perform(get("/api/wallet-transaction-types/{id}", walletTransactionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(walletTransactionType.getId().intValue()))
            .andExpect(jsonPath("$.walletTransTypeCode").value(DEFAULT_WALLET_TRANS_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.walletTransTypeName").value(DEFAULT_WALLET_TRANS_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.walletTransTypeDesc").value(DEFAULT_WALLET_TRANS_TYPE_DESC.toString()))
            .andExpect(jsonPath("$.walletTransTypeFlag").value(DEFAULT_WALLET_TRANS_TYPE_FLAG))
            .andExpect(jsonPath("$.walletTransTypeUDF1").value(DEFAULT_WALLET_TRANS_TYPE_UDF_1.toString()))
            .andExpect(jsonPath("$.walletTransTypeUDF2").value(DEFAULT_WALLET_TRANS_TYPE_UDF_2.toString()))
            .andExpect(jsonPath("$.walletTransTypeUDF3").value(DEFAULT_WALLET_TRANS_TYPE_UDF_3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWalletTransactionType() throws Exception {
        // Get the walletTransactionType
        restWalletTransactionTypeMockMvc.perform(get("/api/wallet-transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalletTransactionType() throws Exception {
        // Initialize the database
        walletTransactionTypeRepository.saveAndFlush(walletTransactionType);

        int databaseSizeBeforeUpdate = walletTransactionTypeRepository.findAll().size();

        // Update the walletTransactionType
        WalletTransactionType updatedWalletTransactionType = walletTransactionTypeRepository.findById(walletTransactionType.getId()).get();
        // Disconnect from session so that the updates on updatedWalletTransactionType are not directly saved in db
        em.detach(updatedWalletTransactionType);
        updatedWalletTransactionType
            .walletTransTypeCode(UPDATED_WALLET_TRANS_TYPE_CODE)
            .walletTransTypeName(UPDATED_WALLET_TRANS_TYPE_NAME)
            .walletTransTypeDesc(UPDATED_WALLET_TRANS_TYPE_DESC)
            .walletTransTypeFlag(UPDATED_WALLET_TRANS_TYPE_FLAG)
            .walletTransTypeUDF1(UPDATED_WALLET_TRANS_TYPE_UDF_1)
            .walletTransTypeUDF2(UPDATED_WALLET_TRANS_TYPE_UDF_2)
            .walletTransTypeUDF3(UPDATED_WALLET_TRANS_TYPE_UDF_3);

        restWalletTransactionTypeMockMvc.perform(put("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWalletTransactionType)))
            .andExpect(status().isOk());

        // Validate the WalletTransactionType in the database
        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeUpdate);
        WalletTransactionType testWalletTransactionType = walletTransactionTypeList.get(walletTransactionTypeList.size() - 1);
        assertThat(testWalletTransactionType.getWalletTransTypeCode()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_CODE);
        assertThat(testWalletTransactionType.getWalletTransTypeName()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_NAME);
        assertThat(testWalletTransactionType.getWalletTransTypeDesc()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_DESC);
        assertThat(testWalletTransactionType.getWalletTransTypeFlag()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_FLAG);
        assertThat(testWalletTransactionType.getWalletTransTypeUDF1()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_UDF_1);
        assertThat(testWalletTransactionType.getWalletTransTypeUDF2()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_UDF_2);
        assertThat(testWalletTransactionType.getWalletTransTypeUDF3()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_UDF_3);

        // Validate the WalletTransactionType in Elasticsearch
        verify(mockWalletTransactionTypeSearchRepository, times(1)).save(testWalletTransactionType);
    }

    @Test
    @Transactional
    public void updateNonExistingWalletTransactionType() throws Exception {
        int databaseSizeBeforeUpdate = walletTransactionTypeRepository.findAll().size();

        // Create the WalletTransactionType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletTransactionTypeMockMvc.perform(put("/api/wallet-transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransactionType)))
            .andExpect(status().isBadRequest());

        // Validate the WalletTransactionType in the database
        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WalletTransactionType in Elasticsearch
        verify(mockWalletTransactionTypeSearchRepository, times(0)).save(walletTransactionType);
    }

    @Test
    @Transactional
    public void deleteWalletTransactionType() throws Exception {
        // Initialize the database
        walletTransactionTypeRepository.saveAndFlush(walletTransactionType);

        int databaseSizeBeforeDelete = walletTransactionTypeRepository.findAll().size();

        // Get the walletTransactionType
        restWalletTransactionTypeMockMvc.perform(delete("/api/wallet-transaction-types/{id}", walletTransactionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WalletTransactionType> walletTransactionTypeList = walletTransactionTypeRepository.findAll();
        assertThat(walletTransactionTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WalletTransactionType in Elasticsearch
        verify(mockWalletTransactionTypeSearchRepository, times(1)).deleteById(walletTransactionType.getId());
    }

    @Test
    @Transactional
    public void searchWalletTransactionType() throws Exception {
        // Initialize the database
        walletTransactionTypeRepository.saveAndFlush(walletTransactionType);
        when(mockWalletTransactionTypeSearchRepository.search(queryStringQuery("id:" + walletTransactionType.getId())))
            .thenReturn(Collections.singletonList(walletTransactionType));
        // Search the walletTransactionType
        restWalletTransactionTypeMockMvc.perform(get("/api/_search/wallet-transaction-types?query=id:" + walletTransactionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletTransactionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletTransTypeCode").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeName").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeDesc").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_DESC.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeFlag").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_FLAG)))
            .andExpect(jsonPath("$.[*].walletTransTypeUDF1").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_UDF_1.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeUDF2").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_UDF_2.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeUDF3").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_UDF_3.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletTransactionType.class);
        WalletTransactionType walletTransactionType1 = new WalletTransactionType();
        walletTransactionType1.setId(1L);
        WalletTransactionType walletTransactionType2 = new WalletTransactionType();
        walletTransactionType2.setId(walletTransactionType1.getId());
        assertThat(walletTransactionType1).isEqualTo(walletTransactionType2);
        walletTransactionType2.setId(2L);
        assertThat(walletTransactionType1).isNotEqualTo(walletTransactionType2);
        walletTransactionType1.setId(null);
        assertThat(walletTransactionType1).isNotEqualTo(walletTransactionType2);
    }
}
