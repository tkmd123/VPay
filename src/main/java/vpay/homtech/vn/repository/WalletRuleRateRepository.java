package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.WalletRuleRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WalletRuleRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletRuleRateRepository extends JpaRepository<WalletRuleRate, Long> {

}
