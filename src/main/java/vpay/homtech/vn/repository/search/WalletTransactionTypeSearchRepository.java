package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.WalletTransactionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WalletTransactionType entity.
 */
public interface WalletTransactionTypeSearchRepository extends ElasticsearchRepository<WalletTransactionType, Long> {
}
