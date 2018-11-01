package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.WalletType;
import vpay.homtech.vn.repository.WalletTypeRepository;
import vpay.homtech.vn.repository.search.WalletTypeSearchRepository;
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
 * Test class for the WalletTypeResource REST controller.
 *
 * @see WalletTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class WalletTypeResourceIntTest {

    private static final String DEFAULT_WALLET_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WALLET_TYPE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_TYPE_DESC = "BBBBBBBBBB";

    @Autowired
    private WalletTypeRepository walletTypeRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.WalletTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalletTypeSearchRepository mockWalletTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWalletTypeMockMvc;

    private WalletType walletType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WalletTypeResource walletTypeResource = new WalletTypeResource(walletTypeRepository, mockWalletTypeSearchRepository);
        this.restWalletTypeMockMvc = MockMvcBuilders.standaloneSetup(walletTypeResource)
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
    public static WalletType createEntity(EntityManager em) {
        WalletType walletType = new WalletType()
            .walletTypeCode(DEFAULT_WALLET_TYPE_CODE)
            .walletTypeName(DEFAULT_WALLET_TYPE_NAME)
            .walletTypeDesc(DEFAULT_WALLET_TYPE_DESC);
        return walletType;
    }

    @Before
    public void initTest() {
        walletType = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalletType() throws Exception {
        int databaseSizeBeforeCreate = walletTypeRepository.findAll().size();

        // Create the WalletType
        restWalletTypeMockMvc.perform(post("/api/wallet-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletType)))
            .andExpect(status().isCreated());

        // Validate the WalletType in the database
        List<WalletType> walletTypeList = walletTypeRepository.findAll();
        assertThat(walletTypeList).hasSize(databaseSizeBeforeCreate + 1);
        WalletType testWalletType = walletTypeList.get(walletTypeList.size() - 1);
        assertThat(testWalletType.getWalletTypeCode()).isEqualTo(DEFAULT_WALLET_TYPE_CODE);
        assertThat(testWalletType.getWalletTypeName()).isEqualTo(DEFAULT_WALLET_TYPE_NAME);
        assertThat(testWalletType.getWalletTypeDesc()).isEqualTo(DEFAULT_WALLET_TYPE_DESC);

        // Validate the WalletType in Elasticsearch
        verify(mockWalletTypeSearchRepository, times(1)).save(testWalletType);
    }

    @Test
    @Transactional
    public void createWalletTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletTypeRepository.findAll().size();

        // Create the WalletType with an existing ID
        walletType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletTypeMockMvc.perform(post("/api/wallet-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletType)))
            .andExpect(status().isBadRequest());

        // Validate the WalletType in the database
        List<WalletType> walletTypeList = walletTypeRepository.findAll();
        assertThat(walletTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the WalletType in Elasticsearch
        verify(mockWalletTypeSearchRepository, times(0)).save(walletType);
    }

    @Test
    @Transactional
    public void getAllWalletTypes() throws Exception {
        // Initialize the database
        walletTypeRepository.saveAndFlush(walletType);

        // Get all the walletTypeList
        restWalletTypeMockMvc.perform(get("/api/wallet-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletType.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletTypeCode").value(hasItem(DEFAULT_WALLET_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].walletTypeName").value(hasItem(DEFAULT_WALLET_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].walletTypeDesc").value(hasItem(DEFAULT_WALLET_TYPE_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getWalletType() throws Exception {
        // Initialize the database
        walletTypeRepository.saveAndFlush(walletType);

        // Get the walletType
        restWalletTypeMockMvc.perform(get("/api/wallet-types/{id}", walletType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(walletType.getId().intValue()))
            .andExpect(jsonPath("$.walletTypeCode").value(DEFAULT_WALLET_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.walletTypeName").value(DEFAULT_WALLET_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.walletTypeDesc").value(DEFAULT_WALLET_TYPE_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWalletType() throws Exception {
        // Get the walletType
        restWalletTypeMockMvc.perform(get("/api/wallet-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalletType() throws Exception {
        // Initialize the database
        walletTypeRepository.saveAndFlush(walletType);

        int databaseSizeBeforeUpdate = walletTypeRepository.findAll().size();

        // Update the walletType
        WalletType updatedWalletType = walletTypeRepository.findById(walletType.getId()).get();
        // Disconnect from session so that the updates on updatedWalletType are not directly saved in db
        em.detach(updatedWalletType);
        updatedWalletType
            .walletTypeCode(UPDATED_WALLET_TYPE_CODE)
            .walletTypeName(UPDATED_WALLET_TYPE_NAME)
            .walletTypeDesc(UPDATED_WALLET_TYPE_DESC);

        restWalletTypeMockMvc.perform(put("/api/wallet-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWalletType)))
            .andExpect(status().isOk());

        // Validate the WalletType in the database
        List<WalletType> walletTypeList = walletTypeRepository.findAll();
        assertThat(walletTypeList).hasSize(databaseSizeBeforeUpdate);
        WalletType testWalletType = walletTypeList.get(walletTypeList.size() - 1);
        assertThat(testWalletType.getWalletTypeCode()).isEqualTo(UPDATED_WALLET_TYPE_CODE);
        assertThat(testWalletType.getWalletTypeName()).isEqualTo(UPDATED_WALLET_TYPE_NAME);
        assertThat(testWalletType.getWalletTypeDesc()).isEqualTo(UPDATED_WALLET_TYPE_DESC);

        // Validate the WalletType in Elasticsearch
        verify(mockWalletTypeSearchRepository, times(1)).save(testWalletType);
    }

    @Test
    @Transactional
    public void updateNonExistingWalletType() throws Exception {
        int databaseSizeBeforeUpdate = walletTypeRepository.findAll().size();

        // Create the WalletType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletTypeMockMvc.perform(put("/api/wallet-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletType)))
            .andExpect(status().isBadRequest());

        // Validate the WalletType in the database
        List<WalletType> walletTypeList = walletTypeRepository.findAll();
        assertThat(walletTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WalletType in Elasticsearch
        verify(mockWalletTypeSearchRepository, times(0)).save(walletType);
    }

    @Test
    @Transactional
    public void deleteWalletType() throws Exception {
        // Initialize the database
        walletTypeRepository.saveAndFlush(walletType);

        int databaseSizeBeforeDelete = walletTypeRepository.findAll().size();

        // Get the walletType
        restWalletTypeMockMvc.perform(delete("/api/wallet-types/{id}", walletType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WalletType> walletTypeList = walletTypeRepository.findAll();
        assertThat(walletTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WalletType in Elasticsearch
        verify(mockWalletTypeSearchRepository, times(1)).deleteById(walletType.getId());
    }

    @Test
    @Transactional
    public void searchWalletType() throws Exception {
        // Initialize the database
        walletTypeRepository.saveAndFlush(walletType);
        when(mockWalletTypeSearchRepository.search(queryStringQuery("id:" + walletType.getId())))
            .thenReturn(Collections.singletonList(walletType));
        // Search the walletType
        restWalletTypeMockMvc.perform(get("/api/_search/wallet-types?query=id:" + walletType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletType.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletTypeCode").value(hasItem(DEFAULT_WALLET_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].walletTypeName").value(hasItem(DEFAULT_WALLET_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].walletTypeDesc").value(hasItem(DEFAULT_WALLET_TYPE_DESC.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletType.class);
        WalletType walletType1 = new WalletType();
        walletType1.setId(1L);
        WalletType walletType2 = new WalletType();
        walletType2.setId(walletType1.getId());
        assertThat(walletType1).isEqualTo(walletType2);
        walletType2.setId(2L);
        assertThat(walletType1).isNotEqualTo(walletType2);
        walletType1.setId(null);
        assertThat(walletType1).isNotEqualTo(walletType2);
    }
}
