package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.WalletTransctionType;
import vpay.homtech.vn.repository.WalletTransctionTypeRepository;
import vpay.homtech.vn.repository.search.WalletTransctionTypeSearchRepository;
import vpay.homtech.vn.web.rest.errors.BadRequestAlertException;
import vpay.homtech.vn.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing WalletTransctionType.
 */
@RestController
@RequestMapping("/api")
public class WalletTransctionTypeResource {

    private final Logger log = LoggerFactory.getLogger(WalletTransctionTypeResource.class);

    private static final String ENTITY_NAME = "walletTransctionType";

    private WalletTransctionTypeRepository walletTransctionTypeRepository;

    private WalletTransctionTypeSearchRepository walletTransctionTypeSearchRepository;

    public WalletTransctionTypeResource(WalletTransctionTypeRepository walletTransctionTypeRepository, WalletTransctionTypeSearchRepository walletTransctionTypeSearchRepository) {
        this.walletTransctionTypeRepository = walletTransctionTypeRepository;
        this.walletTransctionTypeSearchRepository = walletTransctionTypeSearchRepository;
    }

    /**
     * POST  /wallet-transction-types : Create a new walletTransctionType.
     *
     * @param walletTransctionType the walletTransctionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new walletTransctionType, or with status 400 (Bad Request) if the walletTransctionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wallet-transction-types")
    @Timed
    public ResponseEntity<WalletTransctionType> createWalletTransctionType(@Valid @RequestBody WalletTransctionType walletTransctionType) throws URISyntaxException {
        log.debug("REST request to save WalletTransctionType : {}", walletTransctionType);
        if (walletTransctionType.getId() != null) {
            throw new BadRequestAlertException("A new walletTransctionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalletTransctionType result = walletTransctionTypeRepository.save(walletTransctionType);
        walletTransctionTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/wallet-transction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wallet-transction-types : Updates an existing walletTransctionType.
     *
     * @param walletTransctionType the walletTransctionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated walletTransctionType,
     * or with status 400 (Bad Request) if the walletTransctionType is not valid,
     * or with status 500 (Internal Server Error) if the walletTransctionType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wallet-transction-types")
    @Timed
    public ResponseEntity<WalletTransctionType> updateWalletTransctionType(@Valid @RequestBody WalletTransctionType walletTransctionType) throws URISyntaxException {
        log.debug("REST request to update WalletTransctionType : {}", walletTransctionType);
        if (walletTransctionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalletTransctionType result = walletTransctionTypeRepository.save(walletTransctionType);
        walletTransctionTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, walletTransctionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wallet-transction-types : get all the walletTransctionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of walletTransctionTypes in body
     */
    @GetMapping("/wallet-transction-types")
    @Timed
    public List<WalletTransctionType> getAllWalletTransctionTypes() {
        log.debug("REST request to get all WalletTransctionTypes");
        return walletTransctionTypeRepository.findAll();
    }

    /**
     * GET  /wallet-transction-types/:id : get the "id" walletTransctionType.
     *
     * @param id the id of the walletTransctionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the walletTransctionType, or with status 404 (Not Found)
     */
    @GetMapping("/wallet-transction-types/{id}")
    @Timed
    public ResponseEntity<WalletTransctionType> getWalletTransctionType(@PathVariable Long id) {
        log.debug("REST request to get WalletTransctionType : {}", id);
        Optional<WalletTransctionType> walletTransctionType = walletTransctionTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(walletTransctionType);
    }

    /**
     * DELETE  /wallet-transction-types/:id : delete the "id" walletTransctionType.
     *
     * @param id the id of the walletTransctionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wallet-transction-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteWalletTransctionType(@PathVariable Long id) {
        log.debug("REST request to delete WalletTransctionType : {}", id);

        walletTransctionTypeRepository.deleteById(id);
        walletTransctionTypeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wallet-transction-types?query=:query : search for the walletTransctionType corresponding
     * to the query.
     *
     * @param query the query of the walletTransctionType search
     * @return the result of the search
     */
    @GetMapping("/_search/wallet-transction-types")
    @Timed
    public List<WalletTransctionType> searchWalletTransctionTypes(@RequestParam String query) {
        log.debug("REST request to search WalletTransctionTypes for query {}", query);
        return StreamSupport
            .stream(walletTransctionTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
