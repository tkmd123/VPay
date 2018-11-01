package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.WalletTransctionType;
import vpay.homtech.vn.repository.WalletTransctionTypeRepository;
import vpay.homtech.vn.repository.search.WalletTransctionTypeSearchRepository;
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
 * Test class for the WalletTransctionTypeResource REST controller.
 *
 * @see WalletTransctionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class WalletTransctionTypeResourceIntTest {

    private static final String DEFAULT_WALLET_TRANS_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TRANS_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TRANS_TYPE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TRANS_TYPE_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_WALLET_TRANS_TYPE_FLAG = 1;
    private static final Integer UPDATED_WALLET_TRANS_TYPE_FLAG = 2;

    @Autowired
    private WalletTransctionTypeRepository walletTransctionTypeRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.WalletTransctionTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalletTransctionTypeSearchRepository mockWalletTransctionTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWalletTransctionTypeMockMvc;

    private WalletTransctionType walletTransctionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WalletTransctionTypeResource walletTransctionTypeResource = new WalletTransctionTypeResource(walletTransctionTypeRepository, mockWalletTransctionTypeSearchRepository);
        this.restWalletTransctionTypeMockMvc = MockMvcBuilders.standaloneSetup(walletTransctionTypeResource)
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
    public static WalletTransctionType createEntity(EntityManager em) {
        WalletTransctionType walletTransctionType = new WalletTransctionType()
            .walletTransTypeCode(DEFAULT_WALLET_TRANS_TYPE_CODE)
            .walletTransTypeName(DEFAULT_WALLET_TRANS_TYPE_NAME)
            .walletTransTypeDesc(DEFAULT_WALLET_TRANS_TYPE_DESC)
            .walletTransTypeFlag(DEFAULT_WALLET_TRANS_TYPE_FLAG);
        return walletTransctionType;
    }

    @Before
    public void initTest() {
        walletTransctionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalletTransctionType() throws Exception {
        int databaseSizeBeforeCreate = walletTransctionTypeRepository.findAll().size();

        // Create the WalletTransctionType
        restWalletTransctionTypeMockMvc.perform(post("/api/wallet-transction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransctionType)))
            .andExpect(status().isCreated());

        // Validate the WalletTransctionType in the database
        List<WalletTransctionType> walletTransctionTypeList = walletTransctionTypeRepository.findAll();
        assertThat(walletTransctionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WalletTransctionType testWalletTransctionType = walletTransctionTypeList.get(walletTransctionTypeList.size() - 1);
        assertThat(testWalletTransctionType.getWalletTransTypeCode()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_CODE);
        assertThat(testWalletTransctionType.getWalletTransTypeName()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_NAME);
        assertThat(testWalletTransctionType.getWalletTransTypeDesc()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_DESC);
        assertThat(testWalletTransctionType.getWalletTransTypeFlag()).isEqualTo(DEFAULT_WALLET_TRANS_TYPE_FLAG);

        // Validate the WalletTransctionType in Elasticsearch
        verify(mockWalletTransctionTypeSearchRepository, times(1)).save(testWalletTransctionType);
    }

    @Test
    @Transactional
    public void createWalletTransctionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletTransctionTypeRepository.findAll().size();

        // Create the WalletTransctionType with an existing ID
        walletTransctionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletTransctionTypeMockMvc.perform(post("/api/wallet-transction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransctionType)))
            .andExpect(status().isBadRequest());

        // Validate the WalletTransctionType in the database
        List<WalletTransctionType> walletTransctionTypeList = walletTransctionTypeRepository.findAll();
        assertThat(walletTransctionTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the WalletTransctionType in Elasticsearch
        verify(mockWalletTransctionTypeSearchRepository, times(0)).save(walletTransctionType);
    }

    @Test
    @Transactional
    public void checkWalletTransTypeFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = walletTransctionTypeRepository.findAll().size();
        // set the field null
        walletTransctionType.setWalletTransTypeFlag(null);

        // Create the WalletTransctionType, which fails.

        restWalletTransctionTypeMockMvc.perform(post("/api/wallet-transction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransctionType)))
            .andExpect(status().isBadRequest());

        List<WalletTransctionType> walletTransctionTypeList = walletTransctionTypeRepository.findAll();
        assertThat(walletTransctionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWalletTransctionTypes() throws Exception {
        // Initialize the database
        walletTransctionTypeRepository.saveAndFlush(walletTransctionType);

        // Get all the walletTransctionTypeList
        restWalletTransctionTypeMockMvc.perform(get("/api/wallet-transction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletTransctionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletTransTypeCode").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeName").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeDesc").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_DESC.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeFlag").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_FLAG)));
    }
    
    @Test
    @Transactional
    public void getWalletTransctionType() throws Exception {
        // Initialize the database
        walletTransctionTypeRepository.saveAndFlush(walletTransctionType);

        // Get the walletTransctionType
        restWalletTransctionTypeMockMvc.perform(get("/api/wallet-transction-types/{id}", walletTransctionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(walletTransctionType.getId().intValue()))
            .andExpect(jsonPath("$.walletTransTypeCode").value(DEFAULT_WALLET_TRANS_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.walletTransTypeName").value(DEFAULT_WALLET_TRANS_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.walletTransTypeDesc").value(DEFAULT_WALLET_TRANS_TYPE_DESC.toString()))
            .andExpect(jsonPath("$.walletTransTypeFlag").value(DEFAULT_WALLET_TRANS_TYPE_FLAG));
    }

    @Test
    @Transactional
    public void getNonExistingWalletTransctionType() throws Exception {
        // Get the walletTransctionType
        restWalletTransctionTypeMockMvc.perform(get("/api/wallet-transction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalletTransctionType() throws Exception {
        // Initialize the database
        walletTransctionTypeRepository.saveAndFlush(walletTransctionType);

        int databaseSizeBeforeUpdate = walletTransctionTypeRepository.findAll().size();

        // Update the walletTransctionType
        WalletTransctionType updatedWalletTransctionType = walletTransctionTypeRepository.findById(walletTransctionType.getId()).get();
        // Disconnect from session so that the updates on updatedWalletTransctionType are not directly saved in db
        em.detach(updatedWalletTransctionType);
        updatedWalletTransctionType
            .walletTransTypeCode(UPDATED_WALLET_TRANS_TYPE_CODE)
            .walletTransTypeName(UPDATED_WALLET_TRANS_TYPE_NAME)
            .walletTransTypeDesc(UPDATED_WALLET_TRANS_TYPE_DESC)
            .walletTransTypeFlag(UPDATED_WALLET_TRANS_TYPE_FLAG);

        restWalletTransctionTypeMockMvc.perform(put("/api/wallet-transction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWalletTransctionType)))
            .andExpect(status().isOk());

        // Validate the WalletTransctionType in the database
        List<WalletTransctionType> walletTransctionTypeList = walletTransctionTypeRepository.findAll();
        assertThat(walletTransctionTypeList).hasSize(databaseSizeBeforeUpdate);
        WalletTransctionType testWalletTransctionType = walletTransctionTypeList.get(walletTransctionTypeList.size() - 1);
        assertThat(testWalletTransctionType.getWalletTransTypeCode()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_CODE);
        assertThat(testWalletTransctionType.getWalletTransTypeName()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_NAME);
        assertThat(testWalletTransctionType.getWalletTransTypeDesc()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_DESC);
        assertThat(testWalletTransctionType.getWalletTransTypeFlag()).isEqualTo(UPDATED_WALLET_TRANS_TYPE_FLAG);

        // Validate the WalletTransctionType in Elasticsearch
        verify(mockWalletTransctionTypeSearchRepository, times(1)).save(testWalletTransctionType);
    }

    @Test
    @Transactional
    public void updateNonExistingWalletTransctionType() throws Exception {
        int databaseSizeBeforeUpdate = walletTransctionTypeRepository.findAll().size();

        // Create the WalletTransctionType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletTransctionTypeMockMvc.perform(put("/api/wallet-transction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletTransctionType)))
            .andExpect(status().isBadRequest());

        // Validate the WalletTransctionType in the database
        List<WalletTransctionType> walletTransctionTypeList = walletTransctionTypeRepository.findAll();
        assertThat(walletTransctionTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WalletTransctionType in Elasticsearch
        verify(mockWalletTransctionTypeSearchRepository, times(0)).save(walletTransctionType);
    }

    @Test
    @Transactional
    public void deleteWalletTransctionType() throws Exception {
        // Initialize the database
        walletTransctionTypeRepository.saveAndFlush(walletTransctionType);

        int databaseSizeBeforeDelete = walletTransctionTypeRepository.findAll().size();

        // Get the walletTransctionType
        restWalletTransctionTypeMockMvc.perform(delete("/api/wallet-transction-types/{id}", walletTransctionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WalletTransctionType> walletTransctionTypeList = walletTransctionTypeRepository.findAll();
        assertThat(walletTransctionTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WalletTransctionType in Elasticsearch
        verify(mockWalletTransctionTypeSearchRepository, times(1)).deleteById(walletTransctionType.getId());
    }

    @Test
    @Transactional
    public void searchWalletTransctionType() throws Exception {
        // Initialize the database
        walletTransctionTypeRepository.saveAndFlush(walletTransctionType);
        when(mockWalletTransctionTypeSearchRepository.search(queryStringQuery("id:" + walletTransctionType.getId())))
            .thenReturn(Collections.singletonList(walletTransctionType));
        // Search the walletTransctionType
        restWalletTransctionTypeMockMvc.perform(get("/api/_search/wallet-transction-types?query=id:" + walletTransctionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletTransctionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletTransTypeCode").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeName").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeDesc").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_DESC.toString())))
            .andExpect(jsonPath("$.[*].walletTransTypeFlag").value(hasItem(DEFAULT_WALLET_TRANS_TYPE_FLAG)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletTransctionType.class);
        WalletTransctionType walletTransctionType1 = new WalletTransctionType();
        walletTransctionType1.setId(1L);
        WalletTransctionType walletTransctionType2 = new WalletTransctionType();
        walletTransctionType2.setId(walletTransctionType1.getId());
        assertThat(walletTransctionType1).isEqualTo(walletTransctionType2);
        walletTransctionType2.setId(2L);
        assertThat(walletTransctionType1).isNotEqualTo(walletTransctionType2);
        walletTransctionType1.setId(null);
        assertThat(walletTransctionType1).isNotEqualTo(walletTransctionType2);
    }
}
