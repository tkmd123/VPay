package vpay.homtech.vn.web.rest;

import vpay.homtech.vn.VPayApp;

import vpay.homtech.vn.domain.ProductType;
import vpay.homtech.vn.repository.ProductTypeRepository;
import vpay.homtech.vn.repository.search.ProductTypeSearchRepository;
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
 * Test class for the ProductTypeResource REST controller.
 *
 * @see ProductTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VPayApp.class)
public class ProductTypeResourceIntTest {

    private static final String DEFAULT_PRODUCT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_UDF_1 = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_UDF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_UDF_2 = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_UDF_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_UDF_3 = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_UDF_3 = "BBBBBBBBBB";

    @Autowired
    private ProductTypeRepository productTypeRepository;

    /**
     * This repository is mocked in the vpay.homtech.vn.repository.search test package.
     *
     * @see vpay.homtech.vn.repository.search.ProductTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductTypeSearchRepository mockProductTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductTypeMockMvc;

    private ProductType productType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductTypeResource productTypeResource = new ProductTypeResource(productTypeRepository, mockProductTypeSearchRepository);
        this.restProductTypeMockMvc = MockMvcBuilders.standaloneSetup(productTypeResource)
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
    public static ProductType createEntity(EntityManager em) {
        ProductType productType = new ProductType()
            .productTypeCode(DEFAULT_PRODUCT_TYPE_CODE)
            .productTypeName(DEFAULT_PRODUCT_TYPE_NAME)
            .productTypeDesc(DEFAULT_PRODUCT_TYPE_DESC)
            .productUDF1(DEFAULT_PRODUCT_UDF_1)
            .productUDF2(DEFAULT_PRODUCT_UDF_2)
            .productUDF3(DEFAULT_PRODUCT_UDF_3);
        return productType;
    }

    @Before
    public void initTest() {
        productType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductType() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType
        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isCreated());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getProductTypeCode()).isEqualTo(DEFAULT_PRODUCT_TYPE_CODE);
        assertThat(testProductType.getProductTypeName()).isEqualTo(DEFAULT_PRODUCT_TYPE_NAME);
        assertThat(testProductType.getProductTypeDesc()).isEqualTo(DEFAULT_PRODUCT_TYPE_DESC);
        assertThat(testProductType.getProductUDF1()).isEqualTo(DEFAULT_PRODUCT_UDF_1);
        assertThat(testProductType.getProductUDF2()).isEqualTo(DEFAULT_PRODUCT_UDF_2);
        assertThat(testProductType.getProductUDF3()).isEqualTo(DEFAULT_PRODUCT_UDF_3);

        // Validate the ProductType in Elasticsearch
        verify(mockProductTypeSearchRepository, times(1)).save(testProductType);
    }

    @Test
    @Transactional
    public void createProductTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType with an existing ID
        productType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductType in Elasticsearch
        verify(mockProductTypeSearchRepository, times(0)).save(productType);
    }

    @Test
    @Transactional
    public void checkProductTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTypeRepository.findAll().size();
        // set the field null
        productType.setProductTypeCode(null);

        // Create the ProductType, which fails.

        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isBadRequest());

        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTypeRepository.findAll().size();
        // set the field null
        productType.setProductTypeName(null);

        // Create the ProductType, which fails.

        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isBadRequest());

        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductTypes() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList
        restProductTypeMockMvc.perform(get("/api/product-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].productTypeCode").value(hasItem(DEFAULT_PRODUCT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].productTypeName").value(hasItem(DEFAULT_PRODUCT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].productTypeDesc").value(hasItem(DEFAULT_PRODUCT_TYPE_DESC.toString())))
            .andExpect(jsonPath("$.[*].productUDF1").value(hasItem(DEFAULT_PRODUCT_UDF_1.toString())))
            .andExpect(jsonPath("$.[*].productUDF2").value(hasItem(DEFAULT_PRODUCT_UDF_2.toString())))
            .andExpect(jsonPath("$.[*].productUDF3").value(hasItem(DEFAULT_PRODUCT_UDF_3.toString())));
    }
    
    @Test
    @Transactional
    public void getProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get the productType
        restProductTypeMockMvc.perform(get("/api/product-types/{id}", productType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productType.getId().intValue()))
            .andExpect(jsonPath("$.productTypeCode").value(DEFAULT_PRODUCT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.productTypeName").value(DEFAULT_PRODUCT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.productTypeDesc").value(DEFAULT_PRODUCT_TYPE_DESC.toString()))
            .andExpect(jsonPath("$.productUDF1").value(DEFAULT_PRODUCT_UDF_1.toString()))
            .andExpect(jsonPath("$.productUDF2").value(DEFAULT_PRODUCT_UDF_2.toString()))
            .andExpect(jsonPath("$.productUDF3").value(DEFAULT_PRODUCT_UDF_3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductType() throws Exception {
        // Get the productType
        restProductTypeMockMvc.perform(get("/api/product-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Update the productType
        ProductType updatedProductType = productTypeRepository.findById(productType.getId()).get();
        // Disconnect from session so that the updates on updatedProductType are not directly saved in db
        em.detach(updatedProductType);
        updatedProductType
            .productTypeCode(UPDATED_PRODUCT_TYPE_CODE)
            .productTypeName(UPDATED_PRODUCT_TYPE_NAME)
            .productTypeDesc(UPDATED_PRODUCT_TYPE_DESC)
            .productUDF1(UPDATED_PRODUCT_UDF_1)
            .productUDF2(UPDATED_PRODUCT_UDF_2)
            .productUDF3(UPDATED_PRODUCT_UDF_3);

        restProductTypeMockMvc.perform(put("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductType)))
            .andExpect(status().isOk());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getProductTypeCode()).isEqualTo(UPDATED_PRODUCT_TYPE_CODE);
        assertThat(testProductType.getProductTypeName()).isEqualTo(UPDATED_PRODUCT_TYPE_NAME);
        assertThat(testProductType.getProductTypeDesc()).isEqualTo(UPDATED_PRODUCT_TYPE_DESC);
        assertThat(testProductType.getProductUDF1()).isEqualTo(UPDATED_PRODUCT_UDF_1);
        assertThat(testProductType.getProductUDF2()).isEqualTo(UPDATED_PRODUCT_UDF_2);
        assertThat(testProductType.getProductUDF3()).isEqualTo(UPDATED_PRODUCT_UDF_3);

        // Validate the ProductType in Elasticsearch
        verify(mockProductTypeSearchRepository, times(1)).save(testProductType);
    }

    @Test
    @Transactional
    public void updateNonExistingProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Create the ProductType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTypeMockMvc.perform(put("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductType in Elasticsearch
        verify(mockProductTypeSearchRepository, times(0)).save(productType);
    }

    @Test
    @Transactional
    public void deleteProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeDelete = productTypeRepository.findAll().size();

        // Get the productType
        restProductTypeMockMvc.perform(delete("/api/product-types/{id}", productType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductType in Elasticsearch
        verify(mockProductTypeSearchRepository, times(1)).deleteById(productType.getId());
    }

    @Test
    @Transactional
    public void searchProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);
        when(mockProductTypeSearchRepository.search(queryStringQuery("id:" + productType.getId())))
            .thenReturn(Collections.singletonList(productType));
        // Search the productType
        restProductTypeMockMvc.perform(get("/api/_search/product-types?query=id:" + productType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].productTypeCode").value(hasItem(DEFAULT_PRODUCT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].productTypeName").value(hasItem(DEFAULT_PRODUCT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].productTypeDesc").value(hasItem(DEFAULT_PRODUCT_TYPE_DESC.toString())))
            .andExpect(jsonPath("$.[*].productUDF1").value(hasItem(DEFAULT_PRODUCT_UDF_1.toString())))
            .andExpect(jsonPath("$.[*].productUDF2").value(hasItem(DEFAULT_PRODUCT_UDF_2.toString())))
            .andExpect(jsonPath("$.[*].productUDF3").value(hasItem(DEFAULT_PRODUCT_UDF_3.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductType.class);
        ProductType productType1 = new ProductType();
        productType1.setId(1L);
        ProductType productType2 = new ProductType();
        productType2.setId(productType1.getId());
        assertThat(productType1).isEqualTo(productType2);
        productType2.setId(2L);
        assertThat(productType1).isNotEqualTo(productType2);
        productType1.setId(null);
        assertThat(productType1).isNotEqualTo(productType2);
    }
}
