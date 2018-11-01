package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.WalletType;
import vpay.homtech.vn.repository.WalletTypeRepository;
import vpay.homtech.vn.repository.search.WalletTypeSearchRepository;
import vpay.homtech.vn.web.rest.errors.BadRequestAlertException;
import vpay.homtech.vn.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing WalletType.
 */
@RestController
@RequestMapping("/api")
public class WalletTypeResource {

    private final Logger log = LoggerFactory.getLogger(WalletTypeResource.class);

    private static final String ENTITY_NAME = "walletType";

    private WalletTypeRepository walletTypeRepository;

    private WalletTypeSearchRepository walletTypeSearchRepository;

    public WalletTypeResource(WalletTypeRepository walletTypeRepository, WalletTypeSearchRepository walletTypeSearchRepository) {
        this.walletTypeRepository = walletTypeRepository;
        this.walletTypeSearchRepository = walletTypeSearchRepository;
    }

    /**
     * POST  /wallet-types : Create a new walletType.
     *
     * @param walletType the walletType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new walletType, or with status 400 (Bad Request) if the walletType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wallet-types")
    @Timed
    public ResponseEntity<WalletType> createWalletType(@RequestBody WalletType walletType) throws URISyntaxException {
        log.debug("REST request to save WalletType : {}", walletType);
        if (walletType.getId() != null) {
            throw new BadRequestAlertException("A new walletType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalletType result = walletTypeRepository.save(walletType);
        walletTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/wallet-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wallet-types : Updates an existing walletType.
     *
     * @param walletType the walletType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated walletType,
     * or with status 400 (Bad Request) if the walletType is not valid,
     * or with status 500 (Internal Server Error) if the walletType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wallet-types")
    @Timed
    public ResponseEntity<WalletType> updateWalletType(@RequestBody WalletType walletType) throws URISyntaxException {
        log.debug("REST request to update WalletType : {}", walletType);
        if (walletType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalletType result = walletTypeRepository.save(walletType);
        walletTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, walletType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wallet-types : get all the walletTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of walletTypes in body
     */
    @GetMapping("/wallet-types")
    @Timed
    public List<WalletType> getAllWalletTypes() {
        log.debug("REST request to get all WalletTypes");
        return walletTypeRepository.findAll();
    }

    /**
     * GET  /wallet-types/:id : get the "id" walletType.
     *
     * @param id the id of the walletType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the walletType, or with status 404 (Not Found)
     */
    @GetMapping("/wallet-types/{id}")
    @Timed
    public ResponseEntity<WalletType> getWalletType(@PathVariable Long id) {
        log.debug("REST request to get WalletType : {}", id);
        Optional<WalletType> walletType = walletTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(walletType);
    }

    /**
     * DELETE  /wallet-types/:id : delete the "id" walletType.
     *
     * @param id the id of the walletType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wallet-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteWalletType(@PathVariable Long id) {
        log.debug("REST request to delete WalletType : {}", id);

        walletTypeRepository.deleteById(id);
        walletTypeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wallet-types?query=:query : search for the walletType corresponding
     * to the query.
     *
     * @param query the query of the walletType search
     * @return the result of the search
     */
    @GetMapping("/_search/wallet-types")
    @Timed
    public List<WalletType> searchWalletTypes(@RequestParam String query) {
        log.debug("REST request to search WalletTypes for query {}", query);
        return StreamSupport
            .stream(walletTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
