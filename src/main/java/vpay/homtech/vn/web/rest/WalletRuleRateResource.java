package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.WalletRuleRate;
import vpay.homtech.vn.repository.WalletRuleRateRepository;
import vpay.homtech.vn.repository.search.WalletRuleRateSearchRepository;
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
 * REST controller for managing WalletRuleRate.
 */
@RestController
@RequestMapping("/api")
public class WalletRuleRateResource {

    private final Logger log = LoggerFactory.getLogger(WalletRuleRateResource.class);

    private static final String ENTITY_NAME = "walletRuleRate";

    private WalletRuleRateRepository walletRuleRateRepository;

    private WalletRuleRateSearchRepository walletRuleRateSearchRepository;

    public WalletRuleRateResource(WalletRuleRateRepository walletRuleRateRepository, WalletRuleRateSearchRepository walletRuleRateSearchRepository) {
        this.walletRuleRateRepository = walletRuleRateRepository;
        this.walletRuleRateSearchRepository = walletRuleRateSearchRepository;
    }

    /**
     * POST  /wallet-rule-rates : Create a new walletRuleRate.
     *
     * @param walletRuleRate the walletRuleRate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new walletRuleRate, or with status 400 (Bad Request) if the walletRuleRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wallet-rule-rates")
    @Timed
    public ResponseEntity<WalletRuleRate> createWalletRuleRate(@Valid @RequestBody WalletRuleRate walletRuleRate) throws URISyntaxException {
        log.debug("REST request to save WalletRuleRate : {}", walletRuleRate);
        if (walletRuleRate.getId() != null) {
            throw new BadRequestAlertException("A new walletRuleRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalletRuleRate result = walletRuleRateRepository.save(walletRuleRate);
        walletRuleRateSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/wallet-rule-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wallet-rule-rates : Updates an existing walletRuleRate.
     *
     * @param walletRuleRate the walletRuleRate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated walletRuleRate,
     * or with status 400 (Bad Request) if the walletRuleRate is not valid,
     * or with status 500 (Internal Server Error) if the walletRuleRate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wallet-rule-rates")
    @Timed
    public ResponseEntity<WalletRuleRate> updateWalletRuleRate(@Valid @RequestBody WalletRuleRate walletRuleRate) throws URISyntaxException {
        log.debug("REST request to update WalletRuleRate : {}", walletRuleRate);
        if (walletRuleRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalletRuleRate result = walletRuleRateRepository.save(walletRuleRate);
        walletRuleRateSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, walletRuleRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wallet-rule-rates : get all the walletRuleRates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of walletRuleRates in body
     */
    @GetMapping("/wallet-rule-rates")
    @Timed
    public List<WalletRuleRate> getAllWalletRuleRates() {
        log.debug("REST request to get all WalletRuleRates");
        return walletRuleRateRepository.findAll();
    }

    /**
     * GET  /wallet-rule-rates/:id : get the "id" walletRuleRate.
     *
     * @param id the id of the walletRuleRate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the walletRuleRate, or with status 404 (Not Found)
     */
    @GetMapping("/wallet-rule-rates/{id}")
    @Timed
    public ResponseEntity<WalletRuleRate> getWalletRuleRate(@PathVariable Long id) {
        log.debug("REST request to get WalletRuleRate : {}", id);
        Optional<WalletRuleRate> walletRuleRate = walletRuleRateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(walletRuleRate);
    }

    /**
     * DELETE  /wallet-rule-rates/:id : delete the "id" walletRuleRate.
     *
     * @param id the id of the walletRuleRate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wallet-rule-rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteWalletRuleRate(@PathVariable Long id) {
        log.debug("REST request to delete WalletRuleRate : {}", id);

        walletRuleRateRepository.deleteById(id);
        walletRuleRateSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wallet-rule-rates?query=:query : search for the walletRuleRate corresponding
     * to the query.
     *
     * @param query the query of the walletRuleRate search
     * @return the result of the search
     */
    @GetMapping("/_search/wallet-rule-rates")
    @Timed
    public List<WalletRuleRate> searchWalletRuleRates(@RequestParam String query) {
        log.debug("REST request to search WalletRuleRates for query {}", query);
        return StreamSupport
            .stream(walletRuleRateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
