package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.ProductType;
import vpay.homtech.vn.repository.ProductTypeRepository;
import vpay.homtech.vn.repository.search.ProductTypeSearchRepository;
import vpay.homtech.vn.web.rest.errors.BadRequestAlertException;
import vpay.homtech.vn.web.rest.util.HeaderUtil;
import vpay.homtech.vn.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductType.
 */
@RestController
@RequestMapping("/api")
public class ProductTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductTypeResource.class);

    private static final String ENTITY_NAME = "productType";

    private ProductTypeRepository productTypeRepository;

    private ProductTypeSearchRepository productTypeSearchRepository;

    public ProductTypeResource(ProductTypeRepository productTypeRepository, ProductTypeSearchRepository productTypeSearchRepository) {
        this.productTypeRepository = productTypeRepository;
        this.productTypeSearchRepository = productTypeSearchRepository;
    }

    /**
     * POST  /product-types : Create a new productType.
     *
     * @param productType the productType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productType, or with status 400 (Bad Request) if the productType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-types")
    @Timed
    public ResponseEntity<ProductType> createProductType(@Valid @RequestBody ProductType productType) throws URISyntaxException {
        log.debug("REST request to save ProductType : {}", productType);
        if (productType.getId() != null) {
            throw new BadRequestAlertException("A new productType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductType result = productTypeRepository.save(productType);
        productTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/product-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-types : Updates an existing productType.
     *
     * @param productType the productType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productType,
     * or with status 400 (Bad Request) if the productType is not valid,
     * or with status 500 (Internal Server Error) if the productType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-types")
    @Timed
    public ResponseEntity<ProductType> updateProductType(@Valid @RequestBody ProductType productType) throws URISyntaxException {
        log.debug("REST request to update ProductType : {}", productType);
        if (productType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductType result = productTypeRepository.save(productType);
        productTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-types : get all the productTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productTypes in body
     */
    @GetMapping("/product-types")
    @Timed
    public ResponseEntity<List<ProductType>> getAllProductTypes(Pageable pageable) {
        log.debug("REST request to get a page of ProductTypes");
        Page<ProductType> page = productTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-types/:id : get the "id" productType.
     *
     * @param id the id of the productType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productType, or with status 404 (Not Found)
     */
    @GetMapping("/product-types/{id}")
    @Timed
    public ResponseEntity<ProductType> getProductType(@PathVariable Long id) {
        log.debug("REST request to get ProductType : {}", id);
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productType);
    }

    /**
     * DELETE  /product-types/:id : delete the "id" productType.
     *
     * @param id the id of the productType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.debug("REST request to delete ProductType : {}", id);

        productTypeRepository.deleteById(id);
        productTypeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-types?query=:query : search for the productType corresponding
     * to the query.
     *
     * @param query the query of the productType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/product-types")
    @Timed
    public ResponseEntity<List<ProductType>> searchProductTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductTypes for query {}", query);
        Page<ProductType> page = productTypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}