package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.WalletAllotment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WalletAllotment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletAllotmentRepository extends JpaRepository<WalletAllotment, Long> {

}
