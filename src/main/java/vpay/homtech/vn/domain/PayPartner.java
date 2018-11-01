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
 * A PayPartner.
 */
@Entity
@Table(name = "pay_partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paypartner")
public class PayPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_partner_code")
    private String payPartnerCode;

    @Column(name = "pay_partner_name")
    private String payPartnerName;

    @Column(name = "pay_parter_desc")
    private String payParterDesc;

    @OneToMany(mappedBy = "payPartner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wallet> payPartners = new HashSet<>();
    @OneToMany(mappedBy = "payPartner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletRule> payPartners = new HashSet<>();
    @OneToMany(mappedBy = "payPartner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PayPartnerLog> payPartners = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayPartnerCode() {
        return payPartnerCode;
    }

    public PayPartner payPartnerCode(String payPartnerCode) {
        this.payPartnerCode = payPartnerCode;
        return this;
    }

    public void setPayPartnerCode(String payPartnerCode) {
        this.payPartnerCode = payPartnerCode;
    }

    public String getPayPartnerName() {
        return payPartnerName;
    }

    public PayPartner payPartnerName(String payPartnerName) {
        this.payPartnerName = payPartnerName;
        return this;
    }

    public void setPayPartnerName(String payPartnerName) {
        this.payPartnerName = payPartnerName;
    }

    public String getPayParterDesc() {
        return payParterDesc;
    }

    public PayPartner payParterDesc(String payParterDesc) {
        this.payParterDesc = payParterDesc;
        return this;
    }

    public void setPayParterDesc(String payParterDesc) {
        this.payParterDesc = payParterDesc;
    }

    public Set<Wallet> getPayPartners() {
        return payPartners;
    }

    public PayPartner payPartners(Set<Wallet> wallets) {
        this.payPartners = wallets;
        return this;
    }

    public PayPartner addPayPartner(Wallet wallet) {
        this.payPartners.add(wallet);
        wallet.setPayPartner(this);
        return this;
    }

    public PayPartner removePayPartner(Wallet wallet) {
        this.payPartners.remove(wallet);
        wallet.setPayPartner(null);
        return this;
    }

    public void setPayPartners(Set<Wallet> wallets) {
        this.payPartners = wallets;
    }

    public Set<WalletRule> getPayPartners() {
        return payPartners;
    }

    public PayPartner payPartners(Set<WalletRule> walletRules) {
        this.payPartners = walletRules;
        return this;
    }

    public PayPartner addPayPartner(WalletRule walletRule) {
        this.payPartners.add(walletRule);
        walletRule.setPayPartner(this);
        return this;
    }

    public PayPartner removePayPartner(WalletRule walletRule) {
        this.payPartners.remove(walletRule);
        walletRule.setPayPartner(null);
        return this;
    }

    public void setPayPartners(Set<WalletRule> walletRules) {
        this.payPartners = walletRules;
    }

    public Set<PayPartnerLog> getPayPartners() {
        return payPartners;
    }

    public PayPartner payPartners(Set<PayPartnerLog> payPartnerLogs) {
        this.payPartners = payPartnerLogs;
        return this;
    }

    public PayPartner addPayPartner(PayPartnerLog payPartnerLog) {
        this.payPartners.add(payPartnerLog);
        payPartnerLog.setPayPartner(this);
        return this;
    }

    public PayPartner removePayPartner(PayPartnerLog payPartnerLog) {
        this.payPartners.remove(payPartnerLog);
        payPartnerLog.setPayPartner(null);
        return this;
    }

    public void setPayPartners(Set<PayPartnerLog> payPartnerLogs) {
        this.payPartners = payPartnerLogs;
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
        PayPartner payPartner = (PayPartner) o;
        if (payPartner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payPartner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PayPartner{" +
            "id=" + getId() +
            ", payPartnerCode='" + getPayPartnerCode() + "'" +
            ", payPartnerName='" + getPayPartnerName() + "'" +
            ", payParterDesc='" + getPayParterDesc() + "'" +
            "}";
    }
}
