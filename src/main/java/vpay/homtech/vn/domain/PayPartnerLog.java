package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PayPartnerLog.
 */
@Entity
@Table(name = "pay_partner_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paypartnerlog")
public class PayPartnerLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("payPartners")
    private PayPartner payPartner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PayPartner getPayPartner() {
        return payPartner;
    }

    public PayPartnerLog payPartner(PayPartner payPartner) {
        this.payPartner = payPartner;
        return this;
    }

    public void setPayPartner(PayPartner payPartner) {
        this.payPartner = payPartner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PayPartnerLog payPartnerLog = (PayPartnerLog) o;
        if (payPartnerLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payPartnerLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayPartnerLog{" +
            "id=" + getId() +
            "}";
    }
}
