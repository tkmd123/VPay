package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.WalletAllotment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WalletAllotment entity.
 */
public interface WalletAllotmentSearchRepository extends ElasticsearchRepository<WalletAllotment, Long> {
}
