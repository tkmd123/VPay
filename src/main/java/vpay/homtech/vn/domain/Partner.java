package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Partner.
 */
@Entity
@Table(name = "partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partner_code")
    private String partnerCode;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "partner_desc")
    private String partnerDesc;

    @Column(name = "partner_order")
    private Integer partnerOrder;

    @OneToMany(mappedBy = "partner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletAllotment> partners = new HashSet<>();
    @OneToMany(mappedBy = "partner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartnerLog> partners = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public Partner partnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public Partner partnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerDesc() {
        return partnerDesc;
    }

    public Partner partnerDesc(String partnerDesc) {
        this.partnerDesc = partnerDesc;
        return this;
    }

    public void setPartnerDesc(String partnerDesc) {
        this.partnerDesc = partnerDesc;
    }

    public Integer getPartnerOrder() {
        return partnerOrder;
    }

    public Partner partnerOrder(Integer partnerOrder) {
        this.partnerOrder = partnerOrder;
        return this;
    }

    public void setPartnerOrder(Integer partnerOrder) {
        this.partnerOrder = partnerOrder;
    }

    public Set<WalletAllotment> getPartners() {
        return partners;
    }

    public Partner partners(Set<WalletAllotment> walletAllotments) {
        this.partners = walletAllotments;
        return this;
    }

    public Partner addPartner(WalletAllotment walletAllotment) {
        this.partners.add(walletAllotment);
        walletAllotment.setPartner(this);
        return this;
    }

    public Partner removePartner(WalletAllotment walletAllotment) {
        this.partners.remove(walletAllotment);
        walletAllotment.setPartner(null);
        return this;
    }

    public void setPartners(Set<WalletAllotment> walletAllotments) {
        this.partners = walletAllotments;
    }

    public Set<PartnerLog> getPartners() {
        return partners;
    }

    public Partner partners(Set<PartnerLog> partnerLogs) {
        this.partners = partnerLogs;
        return this;
    }

    public Partner addPartner(PartnerLog partnerLog) {
        this.partners.add(partnerLog);
        partnerLog.setPartner(this);
        return this;
    }

    public Partner removePartner(PartnerLog partnerLog) {
        this.partners.remove(partnerLog);
        partnerLog.setPartner(null);
        return this;
    }

    public void setPartners(Set<PartnerLog> partnerLogs) {
        this.partners = partnerLogs;
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
        Partner partner = (Partner) o;
        if (partner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + getId() +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", partnerName='" + getPartnerName() + "'" +
            ", partnerDesc='" + getPartnerDesc() + "'" +
            ", partnerOrder=" + getPartnerOrder() +
            "}";
    }
}
