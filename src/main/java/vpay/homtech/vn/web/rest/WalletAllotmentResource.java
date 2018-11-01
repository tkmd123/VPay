package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.WalletAllotment;
import vpay.homtech.vn.repository.WalletAllotmentRepository;
import vpay.homtech.vn.repository.search.WalletAllotmentSearchRepository;
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
 * REST controller for managing WalletAllotment.
 */
@RestController
@RequestMapping("/api")
public class WalletAllotmentResource {

    private final Logger log = LoggerFactory.getLogger(WalletAllotmentResource.class);

    private static final String ENTITY_NAME = "walletAllotment";

    private WalletAllotmentRepository walletAllotmentRepository;

    private WalletAllotmentSearchRepository walletAllotmentSearchRepository;

    public WalletAllotmentResource(WalletAllotmentRepository walletAllotmentRepository, WalletAllotmentSearchRepository walletAllotmentSearchRepository) {
        this.walletAllotmentRepository = walletAllotmentRepository;
        this.walletAllotmentSearchRepository = walletAllotmentSearchRepository;
    }

    /**
     * POST  /wallet-allotments : Create a new walletAllotment.
     *
     * @param walletAllotment the walletAllotment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new walletAllotment, or with status 400 (Bad Request) if the walletAllotment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wallet-allotments")
    @Timed
    public ResponseEntity<WalletAllotment> createWalletAllotment(@RequestBody WalletAllotment walletAllotment) throws URISyntaxException {
        log.debug("REST request to save WalletAllotment : {}", walletAllotment);
        if (walletAllotment.getId() != null) {
            throw new BadRequestAlertException("A new walletAllotment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalletAllotment result = walletAllotmentRepository.save(walletAllotment);
        walletAllotmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/wallet-allotments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wallet-allotments : Updates an existing walletAllotment.
     *
     * @param walletAllotment the walletAllotment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated walletAllotment,
     * or with status 400 (Bad Request) if the walletAllotment is not valid,
     * or with status 500 (Internal Server Error) if the walletAllotment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wallet-allotments")
    @Timed
    public ResponseEntity<WalletAllotment> updateWalletAllotment(@RequestBody WalletAllotment walletAllotment) throws URISyntaxException {
        log.debug("REST request to update WalletAllotment : {}", walletAllotment);
        if (walletAllotment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WalletAllotment result = walletAllotmentRepository.save(walletAllotment);
        walletAllotmentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, walletAllotment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wallet-allotments : get all the walletAllotments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of walletAllotments in body
     */
    @GetMapping("/wallet-allotments")
    @Timed
    public List<WalletAllotment> getAllWalletAllotments() {
        log.debug("REST request to get all WalletAllotments");
        return walletAllotmentRepository.findAll();
    }

    /**
     * GET  /wallet-allotments/:id : get the "id" walletAllotment.
     *
     * @param id the id of the walletAllotment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the walletAllotment, or with status 404 (Not Found)
     */
    @GetMapping("/wallet-allotments/{id}")
    @Timed
    public ResponseEntity<WalletAllotment> getWalletAllotment(@PathVariable Long id) {
        log.debug("REST request to get WalletAllotment : {}", id);
        Optional<WalletAllotment> walletAllotment = walletAllotmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(walletAllotment);
    }

    /**
     * DELETE  /wallet-allotments/:id : delete the "id" walletAllotment.
     *
     * @param id the id of the walletAllotment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wallet-allotments/{id}")
    @Timed
    public ResponseEntity<Void> deleteWalletAllotment(@PathVariable Long id) {
        log.debug("REST request to delete WalletAllotment : {}", id);

        walletAllotmentRepository.deleteById(id);
        walletAllotmentSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wallet-allotments?query=:query : search for the walletAllotment corresponding
     * to the query.
     *
     * @param query the query of the walletAllotment search
     * @return the result of the search
     */
    @GetMapping("/_search/wallet-allotments")
    @Timed
    public List<WalletAllotment> searchWalletAllotments(@RequestParam String query) {
        log.debug("REST request to search WalletAllotments for query {}", query);
        return StreamSupport
            .stream(walletAllotmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
