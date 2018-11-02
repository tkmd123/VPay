package vpay.homtech.vn.web.rest;

import com.codahale.metrics.annotation.Timed;
import vpay.homtech.vn.domain.PartnerLog;
import vpay.homtech.vn.repository.PartnerLogRepository;
import vpay.homtech.vn.repository.search.PartnerLogSearchRepository;
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
 * REST controller for managing PartnerLog.
 */
@RestController
@RequestMapping("/api")
public class PartnerLogResource {

    private final Logger log = LoggerFactory.getLogger(PartnerLogResource.class);

    private static final String ENTITY_NAME = "partnerLog";

    private PartnerLogRepository partnerLogRepository;

    private PartnerLogSearchRepository partnerLogSearchRepository;

    public PartnerLogResource(PartnerLogRepository partnerLogRepository, PartnerLogSearchRepository partnerLogSearchRepository) {
        this.partnerLogRepository = partnerLogRepository;
        this.partnerLogSearchRepository = partnerLogSearchRepository;
    }

    /**
     * POST  /partner-logs : Create a new partnerLog.
     *
     * @param partnerLog the partnerLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partnerLog, or with status 400 (Bad Request) if the partnerLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partner-logs")
    @Timed
    public ResponseEntity<PartnerLog> createPartnerLog(@RequestBody PartnerLog partnerLog) throws URISyntaxException {
        log.debug("REST request to save PartnerLog : {}", partnerLog);
        if (partnerLog.getId() != null) {
            throw new BadRequestAlertException("A new partnerLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerLog result = partnerLogRepository.save(partnerLog);
        partnerLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/partner-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partner-logs : Updates an existing partnerLog.
     *
     * @param partnerLog the partnerLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partnerLog,
     * or with status 400 (Bad Request) if the partnerLog is not valid,
     * or with status 500 (Internal Server Error) if the partnerLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partner-logs")
    @Timed
    public ResponseEntity<PartnerLog> updatePartnerLog(@RequestBody PartnerLog partnerLog) throws URISyntaxException {
        log.debug("REST request to update PartnerLog : {}", partnerLog);
        if (partnerLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerLog result = partnerLogRepository.save(partnerLog);
        partnerLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partnerLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partner-logs : get all the partnerLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partnerLogs in body
     */
    @GetMapping("/partner-logs")
    @Timed
    public List<PartnerLog> getAllPartnerLogs() {
        log.debug("REST request to get all PartnerLogs");
        return partnerLogRepository.findAll();
    }

    /**
     * GET  /partner-logs/:id : get the "id" partnerLog.
     *
     * @param id the id of the partnerLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partnerLog, or with status 404 (Not Found)
     */
    @GetMapping("/partner-logs/{id}")
    @Timed
    public ResponseEntity<PartnerLog> getPartnerLog(@PathVariable Long id) {
        log.debug("REST request to get PartnerLog : {}", id);
        Optional<PartnerLog> partnerLog = partnerLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partnerLog);
    }

    /**
     * DELETE  /partner-logs/:id : delete the "id" partnerLog.
     *
     * @param id the id of the partnerLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partner-logs/{id}")
    @Timed
    public ResponseEntity<Void> deletePartnerLog(@PathVariable Long id) {
        log.debug("REST request to delete PartnerLog : {}", id);

        partnerLogRepository.deleteById(id);
        partnerLogSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/partner-logs?query=:query : search for the partnerLog corresponding
     * to the query.
     *
     * @param query the query of the partnerLog search
     * @return the result of the search
     */
    @GetMapping("/_search/partner-logs")
    @Timed
    public List<PartnerLog> searchPartnerLogs(@RequestParam String query) {
        log.debug("REST request to search PartnerLogs for query {}", query);
        return StreamSupport
            .stream(partnerLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
