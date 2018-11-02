package vpay.homtech.vn.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of WalletTransactionSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WalletTransactionSearchRepositoryMockConfiguration {

    @MockBean
    private WalletTransactionSearchRepository mockWalletTransactionSearchRepository;

}
