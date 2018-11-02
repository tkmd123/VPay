package vpay.homtech.vn.repository;

import vpay.homtech.vn.domain.PayPartner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PayPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayPartnerRepository extends JpaRepository<PayPartner, Long> {

}
