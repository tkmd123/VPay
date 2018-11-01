package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.WalletType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WalletType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {

}
