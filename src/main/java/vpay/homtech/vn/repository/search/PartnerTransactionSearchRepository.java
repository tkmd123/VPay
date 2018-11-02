package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.PartnerTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PartnerTransaction entity.
 */
public interface PartnerTransactionSearchRepository extends ElasticsearchRepository<PartnerTransaction, Long> {
}
