package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WalletRule.
 */
@Entity
@Table(name = "wallet_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "walletrule")
public class WalletRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_rule_code")
    private String walletRuleCode;

    @Column(name = "wallet_rule_name")
    private String walletRuleName;

    @Column(name = "wallet_rule_desc")
    private String walletRuleDesc;

    @Column(name = "wallet_rule_from_date")
    private Instant walletRuleFromDate;

    @Column(name = "wallet_rule_to_date")
    private Instant walletRuleToDate;

    @ManyToOne
    @JsonIgnoreProperties("walletTypes")
    private WalletType walletType;

    @ManyToOne
    @JsonIgnoreProperties("payPartners")
    private PayPartner payPartner;

    @OneToMany(mappedBy = "walletRule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletRuleRate> walletRules = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletRuleCode() {
        return walletRuleCode;
    }

    public WalletRule walletRuleCode(String walletRuleCode) {
        this.walletRuleCode = walletRuleCode;
        return this;
    }

    public void setWalletRuleCode(String walletRuleCode) {
        this.walletRuleCode = walletRuleCode;
    }

    public String getWalletRuleName() {
        return walletRuleName;
    }

    public WalletRule walletRuleName(String walletRuleName) {
        this.walletRuleName = walletRuleName;
        return this;
    }

    public void setWalletRuleName(String walletRuleName) {
        this.walletRuleName = walletRuleName;
    }

    public String getWalletRuleDesc() {
        return walletRuleDesc;
    }

    public WalletRule walletRuleDesc(String walletRuleDesc) {
        this.walletRuleDesc = walletRuleDesc;
        return this;
    }

    public void setWalletRuleDesc(String walletRuleDesc) {
        this.walletRuleDesc = walletRuleDesc;
    }

    public Instant getWalletRuleFromDate() {
        return walletRuleFromDate;
    }

    public WalletRule walletRuleFromDate(Instant walletRuleFromDate) {
        this.walletRuleFromDate = walletRuleFromDate;
        return this;
    }

    public void setWalletRuleFromDate(Instant walletRuleFromDate) {
        this.walletRuleFromDate = walletRuleFromDate;
    }

    public Instant getWalletRuleToDate() {
        return walletRuleToDate;
    }

    public WalletRule walletRuleToDate(Instant walletRuleToDate) {
        this.walletRuleToDate = walletRuleToDate;
        return this;
    }

    public void setWalletRuleToDate(Instant walletRuleToDate) {
        this.walletRuleToDate = walletRuleToDate;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public WalletRule walletType(WalletType walletType) {
        this.walletType = walletType;
        return this;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public PayPartner getPayPartner() {
        return payPartner;
    }

    public WalletRule payPartner(PayPartner payPartner) {
        this.payPartner = payPartner;
        return this;
    }

    public void setPayPartner(PayPartner payPartner) {
        this.payPartner = payPartner;
    }

    public Set<WalletRuleRate> getWalletRules() {
        return walletRules;
    }

    public WalletRule walletRules(Set<WalletRuleRate> walletRuleRates) {
        this.walletRules = walletRuleRates;
        return this;
    }

    public WalletRule addWalletRule(WalletRuleRate walletRuleRate) {
        this.walletRules.add(walletRuleRate);
        walletRuleRate.setWalletRule(this);
        return this;
    }

    public WalletRule removeWalletRule(WalletRuleRate walletRuleRate) {
        this.walletRules.remove(walletRuleRate);
        walletRuleRate.setWalletRule(null);
        return this;
    }

    public void setWalletRules(Set<WalletRuleRate> walletRuleRates) {
        this.walletRules = walletRuleRates;
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
        WalletRule walletRule = (WalletRule) o;
        if (walletRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletRule{" +
            "id=" + getId() +
            ", walletRuleCode='" + getWalletRuleCode() + "'" +
            ", walletRuleName='" + getWalletRuleName() + "'" +
            ", walletRuleDesc='" + getWalletRuleDesc() + "'" +
            ", walletRuleFromDate='" + getWalletRuleFromDate() + "'" +
            ", walletRuleToDate='" + getWalletRuleToDate() + "'" +
            "}";
    }
}
