package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.WalletTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WalletTransaction entity.
 */
public interface WalletTransactionSearchRepository extends ElasticsearchRepository<WalletTransaction, Long> {
}
