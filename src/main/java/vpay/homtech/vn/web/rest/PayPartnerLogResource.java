package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.PayPartnerLog;
import vpay.homtech.vn.repository.PayPartnerLogRepository;
import vpay.homtech.vn.repository.search.PayPartnerLogSearchRepository;
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
 * REST controller for managing PayPartnerLog.
 */
@RestController
@RequestMapping("/api")
public class PayPartnerLogResource {

    private final Logger log = LoggerFactory.getLogger(PayPartnerLogResource.class);

    private static final String ENTITY_NAME = "payPartnerLog";

    private PayPartnerLogRepository payPartnerLogRepository;

    private PayPartnerLogSearchRepository payPartnerLogSearchRepository;

    public PayPartnerLogResource(PayPartnerLogRepository payPartnerLogRepository, PayPartnerLogSearchRepository payPartnerLogSearchRepository) {
        this.payPartnerLogRepository = payPartnerLogRepository;
        this.payPartnerLogSearchRepository = payPartnerLogSearchRepository;
    }

    /**
     * POST  /pay-partner-logs : Create a new payPartnerLog.
     *
     * @param payPartnerLog the payPartnerLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payPartnerLog, or with status 400 (Bad Request) if the payPartnerLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pay-partner-logs")
    @Timed
    public ResponseEntity<PayPartnerLog> createPayPartnerLog(@RequestBody PayPartnerLog payPartnerLog) throws URISyntaxException {
        log.debug("REST request to save PayPartnerLog : {}", payPartnerLog);
        if (payPartnerLog.getId() != null) {
            throw new BadRequestAlertException("A new payPartnerLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayPartnerLog result = payPartnerLogRepository.save(payPartnerLog);
        payPartnerLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pay-partner-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pay-partner-logs : Updates an existing payPartnerLog.
     *
     * @param payPartnerLog the payPartnerLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payPartnerLog,
     * or with status 400 (Bad Request) if the payPartnerLog is not valid,
     * or with status 500 (Internal Server Error) if the payPartnerLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pay-partner-logs")
    @Timed
    public ResponseEntity<PayPartnerLog> updatePayPartnerLog(@RequestBody PayPartnerLog payPartnerLog) throws URISyntaxException {
        log.debug("REST request to update PayPartnerLog : {}", payPartnerLog);
        if (payPartnerLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PayPartnerLog result = payPartnerLogRepository.save(payPartnerLog);
        payPartnerLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payPartnerLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pay-partner-logs : get all the payPartnerLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payPartnerLogs in body
     */
    @GetMapping("/pay-partner-logs")
    @Timed
    public List<PayPartnerLog> getAllPayPartnerLogs() {
        log.debug("REST request to get all PayPartnerLogs");
        return payPartnerLogRepository.findAll();
    }

    /**
     * GET  /pay-partner-logs/:id : get the "id" payPartnerLog.
     *
     * @param id the id of the payPartnerLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payPartnerLog, or with status 404 (Not Found)
     */
    @GetMapping("/pay-partner-logs/{id}")
    @Timed
    public ResponseEntity<PayPartnerLog> getPayPartnerLog(@PathVariable Long id) {
        log.debug("REST request to get PayPartnerLog : {}", id);
        Optional<PayPartnerLog> payPartnerLog = payPartnerLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payPartnerLog);
    }

    /**
     * DELETE  /pay-partner-logs/:id : delete the "id" payPartnerLog.
     *
     * @param id the id of the payPartnerLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pay-partner-logs/{id}")
    @Timed
    public ResponseEntity<Void> deletePayPartnerLog(@PathVariable Long id) {
        log.debug("REST request to delete PayPartnerLog : {}", id);

        payPartnerLogRepository.deleteById(id);
        payPartnerLogSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pay-partner-logs?query=:query : search for the payPartnerLog corresponding
     * to the query.
     *
     * @param query the query of the payPartnerLog search
     * @return the result of the search
     */
    @GetMapping("/_search/pay-partner-logs")
    @Timed
    public List<PayPartnerLog> searchPayPartnerLogs(@RequestParam String query) {
        log.debug("REST request to search PayPartnerLogs for query {}", query);
        return StreamSupport
            .stream(payPartnerLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
