package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.PartnerTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartnerTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerTransactionRepository extends JpaRepository<PartnerTransaction, Long> {

}
