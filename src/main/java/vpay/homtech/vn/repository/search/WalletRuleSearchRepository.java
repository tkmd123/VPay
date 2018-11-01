package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.WalletRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WalletRule entity.
 */
public interface WalletRuleSearchRepository extends ElasticsearchRepository<WalletRule, Long> {
}
