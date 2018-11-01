package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.WalletRuleRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WalletRuleRate entity.
 */
public interface WalletRuleRateSearchRepository extends ElasticsearchRepository<WalletRuleRate, Long> {
}
