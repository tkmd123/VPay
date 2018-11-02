package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.WalletRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WalletRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletRuleRepository extends JpaRepository<WalletRule, Long> {

}
