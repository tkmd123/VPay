package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.PayPartner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PayPartner entity.
 */
public interface PayPartnerSearchRepository extends ElasticsearchRepository<PayPartner, Long> {
}
