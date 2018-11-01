package vpay.homtech.vn.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PartnerLogSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PartnerLogSearchRepositoryMockConfiguration {

    @MockBean
    private PartnerLogSearchRepository mockPartnerLogSearchRepository;

}
