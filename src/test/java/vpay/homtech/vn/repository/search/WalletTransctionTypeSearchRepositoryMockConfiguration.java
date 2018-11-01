package vpay.homtech.vn.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of WalletTransctionTypeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WalletTransctionTypeSearchRepositoryMockConfiguration {

    @MockBean
    private WalletTransctionTypeSearchRepository mockWalletTransctionTypeSearchRepository;

}
