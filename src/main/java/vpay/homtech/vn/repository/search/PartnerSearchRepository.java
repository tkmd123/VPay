package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.Partner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Partner entity.
 */
public interface PartnerSearchRepository extends ElasticsearchRepository<Partner, Long> {
}
