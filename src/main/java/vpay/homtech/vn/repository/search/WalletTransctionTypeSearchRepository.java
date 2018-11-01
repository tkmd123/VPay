package vpay.homtech.vn.repository.search;

import vpay.homtech.vn.domain.WalletTransctionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WalletTransctionType entity.
 */
public interface WalletTransctionTypeSearchRepository extends ElasticsearchRepository<WalletTransctionType, Long> {
}
