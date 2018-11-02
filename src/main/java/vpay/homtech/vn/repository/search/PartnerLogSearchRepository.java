package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.PartnerLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PartnerLog entity.
 */
public interface PartnerLogSearchRepository extends ElasticsearchRepository<PartnerLog, Long> {
}
