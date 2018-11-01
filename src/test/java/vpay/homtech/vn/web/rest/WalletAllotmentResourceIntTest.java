package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.WalletAllotment;
import vpay.homtech.vn.repository.WalletAllotmentRepository;
import vpay.homtech.vn.repository.search.WalletAllotmentSearchRepository;
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
 * Test class for the WalletAllotmentResource REST controller.
 *
 * @see WalletAllotmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class WalletAllotmentResourceIntTest {

    private static final Long DEFAULT_WALLET_ALLOTMENT_AMOUNT = 1L;
    private static final Long UPDATED_WALLET_ALLOTMENT_AMOUNT = 2L;

    @Autowired
    private WalletAllotmentRepository walletAllotmentRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.WalletAllotmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private WalletAllotmentSearchRepository mockWalletAllotmentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWalletAllotmentMockMvc;

    private WalletAllotment walletAllotment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WalletAllotmentResource walletAllotmentResource = new WalletAllotmentResource(walletAllotmentRepository, mockWalletAllotmentSearchRepository);
        this.restWalletAllotmentMockMvc = MockMvcBuilders.standaloneSetup(walletAllotmentResource)
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
    public static WalletAllotment createEntity(EntityManager em) {
        WalletAllotment walletAllotment = new WalletAllotment()
            .walletAllotmentAmount(DEFAULT_WALLET_ALLOTMENT_AMOUNT);
        return walletAllotment;
    }

    @Before
    public void initTest() {
        walletAllotment = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalletAllotment() throws Exception {
        int databaseSizeBeforeCreate = walletAllotmentRepository.findAll().size();

        // Create the WalletAllotment
        restWalletAllotmentMockMvc.perform(post("/api/wallet-allotments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletAllotment)))
            .andExpect(status().isCreated());

        // Validate the WalletAllotment in the database
        List<WalletAllotment> walletAllotmentList = walletAllotmentRepository.findAll();
        assertThat(walletAllotmentList).hasSize(databaseSizeBeforeCreate + 1);
        WalletAllotment testWalletAllotment = walletAllotmentList.get(walletAllotmentList.size() - 1);
        assertThat(testWalletAllotment.getWalletAllotmentAmount()).isEqualTo(DEFAULT_WALLET_ALLOTMENT_AMOUNT);

        // Validate the WalletAllotment in Elasticsearch
        verify(mockWalletAllotmentSearchRepository, times(1)).save(testWalletAllotment);
    }

    @Test
    @Transactional
    public void createWalletAllotmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = walletAllotmentRepository.findAll().size();

        // Create the WalletAllotment with an existing ID
        walletAllotment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWalletAllotmentMockMvc.perform(post("/api/wallet-allotments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletAllotment)))
            .andExpect(status().isBadRequest());

        // Validate the WalletAllotment in the database
        List<WalletAllotment> walletAllotmentList = walletAllotmentRepository.findAll();
        assertThat(walletAllotmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the WalletAllotment in Elasticsearch
        verify(mockWalletAllotmentSearchRepository, times(0)).save(walletAllotment);
    }

    @Test
    @Transactional
    public void getAllWalletAllotments() throws Exception {
        // Initialize the database
        walletAllotmentRepository.saveAndFlush(walletAllotment);

        // Get all the walletAllotmentList
        restWalletAllotmentMockMvc.perform(get("/api/wallet-allotments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletAllotment.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletAllotmentAmount").value(hasItem(DEFAULT_WALLET_ALLOTMENT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getWalletAllotment() throws Exception {
        // Initialize the database
        walletAllotmentRepository.saveAndFlush(walletAllotment);

        // Get the walletAllotment
        restWalletAllotmentMockMvc.perform(get("/api/wallet-allotments/{id}", walletAllotment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(walletAllotment.getId().intValue()))
            .andExpect(jsonPath("$.walletAllotmentAmount").value(DEFAULT_WALLET_ALLOTMENT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWalletAllotment() throws Exception {
        // Get the walletAllotment
        restWalletAllotmentMockMvc.perform(get("/api/wallet-allotments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalletAllotment() throws Exception {
        // Initialize the database
        walletAllotmentRepository.saveAndFlush(walletAllotment);

        int databaseSizeBeforeUpdate = walletAllotmentRepository.findAll().size();

        // Update the walletAllotment
        WalletAllotment updatedWalletAllotment = walletAllotmentRepository.findById(walletAllotment.getId()).get();
        // Disconnect from session so that the updates on updatedWalletAllotment are not directly saved in db
        em.detach(updatedWalletAllotment);
        updatedWalletAllotment
            .walletAllotmentAmount(UPDATED_WALLET_ALLOTMENT_AMOUNT);

        restWalletAllotmentMockMvc.perform(put("/api/wallet-allotments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWalletAllotment)))
            .andExpect(status().isOk());

        // Validate the WalletAllotment in the database
        List<WalletAllotment> walletAllotmentList = walletAllotmentRepository.findAll();
        assertThat(walletAllotmentList).hasSize(databaseSizeBeforeUpdate);
        WalletAllotment testWalletAllotment = walletAllotmentList.get(walletAllotmentList.size() - 1);
        assertThat(testWalletAllotment.getWalletAllotmentAmount()).isEqualTo(UPDATED_WALLET_ALLOTMENT_AMOUNT);

        // Validate the WalletAllotment in Elasticsearch
        verify(mockWalletAllotmentSearchRepository, times(1)).save(testWalletAllotment);
    }

    @Test
    @Transactional
    public void updateNonExistingWalletAllotment() throws Exception {
        int databaseSizeBeforeUpdate = walletAllotmentRepository.findAll().size();

        // Create the WalletAllotment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWalletAllotmentMockMvc.perform(put("/api/wallet-allotments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(walletAllotment)))
            .andExpect(status().isBadRequest());

        // Validate the WalletAllotment in the database
        List<WalletAllotment> walletAllotmentList = walletAllotmentRepository.findAll();
        assertThat(walletAllotmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WalletAllotment in Elasticsearch
        verify(mockWalletAllotmentSearchRepository, times(0)).save(walletAllotment);
    }

    @Test
    @Transactional
    public void deleteWalletAllotment() throws Exception {
        // Initialize the database
        walletAllotmentRepository.saveAndFlush(walletAllotment);

        int databaseSizeBeforeDelete = walletAllotmentRepository.findAll().size();

        // Get the walletAllotment
        restWalletAllotmentMockMvc.perform(delete("/api/wallet-allotments/{id}", walletAllotment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WalletAllotment> walletAllotmentList = walletAllotmentRepository.findAll();
        assertThat(walletAllotmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WalletAllotment in Elasticsearch
        verify(mockWalletAllotmentSearchRepository, times(1)).deleteById(walletAllotment.getId());
    }

    @Test
    @Transactional
    public void searchWalletAllotment() throws Exception {
        // Initialize the database
        walletAllotmentRepository.saveAndFlush(walletAllotment);
        when(mockWalletAllotmentSearchRepository.search(queryStringQuery("id:" + walletAllotment.getId())))
            .thenReturn(Collections.singletonList(walletAllotment));
        // Search the walletAllotment
        restWalletAllotmentMockMvc.perform(get("/api/_search/wallet-allotments?query=id:" + walletAllotment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walletAllotment.getId().intValue())))
            .andExpect(jsonPath("$.[*].walletAllotmentAmount").value(hasItem(DEFAULT_WALLET_ALLOTMENT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletAllotment.class);
        WalletAllotment walletAllotment1 = new WalletAllotment();
        walletAllotment1.setId(1L);
        WalletAllotment walletAllotment2 = new WalletAllotment();
        walletAllotment2.setId(walletAllotment1.getId());
        assertThat(walletAllotment1).isEqualTo(walletAllotment2);
        walletAllotment2.setId(2L);
        assertThat(walletAllotment1).isNotEqualTo(walletAllotment2);
        walletAllotment1.setId(null);
        assertThat(walletAllotment1).isNotEqualTo(walletAllotment2);
    }
}
