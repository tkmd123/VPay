package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.WalletTransctionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WalletTransctionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletTransctionTypeRepository extends JpaRepository<WalletTransctionType, Long> {

}
