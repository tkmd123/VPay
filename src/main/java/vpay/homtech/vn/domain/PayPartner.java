package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotNull
    @Column(name = "pay_partner_code", nullable = false)
    private String payPartnerCode;

    @NotNull
    @Column(name = "pay_partner_name", nullable = false)
    private String payPartnerName;

    @Column(name = "pay_partner_desc")
    private String payPartnerDesc;

    @Column(name = "pay_partner_udf_1")
    private String payPartnerUDF1;

    @Column(name = "pay_partner_udf_2")
    private String payPartnerUDF2;

    @Column(name = "pay_partner_udf_3")
    private String payPartnerUDF3;

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

    public String getPayPartnerDesc() {
        return payPartnerDesc;
    }

    public PayPartner payPartnerDesc(String payPartnerDesc) {
        this.payPartnerDesc = payPartnerDesc;
        return this;
    }

    public void setPayPartnerDesc(String payPartnerDesc) {
        this.payPartnerDesc = payPartnerDesc;
    }

    public String getPayPartnerUDF1() {
        return payPartnerUDF1;
    }

    public PayPartner payPartnerUDF1(String payPartnerUDF1) {
        this.payPartnerUDF1 = payPartnerUDF1;
        return this;
    }

    public void setPayPartnerUDF1(String payPartnerUDF1) {
        this.payPartnerUDF1 = payPartnerUDF1;
    }

    public String getPayPartnerUDF2() {
        return payPartnerUDF2;
    }

    public PayPartner payPartnerUDF2(String payPartnerUDF2) {
        this.payPartnerUDF2 = payPartnerUDF2;
        return this;
    }

    public void setPayPartnerUDF2(String payPartnerUDF2) {
        this.payPartnerUDF2 = payPartnerUDF2;
    }

    public String getPayPartnerUDF3() {
        return payPartnerUDF3;
    }

    public PayPartner payPartnerUDF3(String payPartnerUDF3) {
        this.payPartnerUDF3 = payPartnerUDF3;
        return this;
    }

    public void setPayPartnerUDF3(String payPartnerUDF3) {
        this.payPartnerUDF3 = payPartnerUDF3;
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
            ", payPartnerDesc='" + getPayPartnerDesc() + "'" +
            ", payPartnerUDF1='" + getPayPartnerUDF1() + "'" +
            ", payPartnerUDF2='" + getPayPartnerUDF2() + "'" +
            ", payPartnerUDF3='" + getPayPartnerUDF3() + "'" +
            "}";
    }
}
