package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WalletRuleRate.
 */
@Entity
@Table(name = "wallet_rule_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "walletrulerate")
public class WalletRuleRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_rule_rate_code")
    private String walletRuleRateCode;

    @Column(name = "wallet_rule_rate_name")
    private String walletRuleRateName;

    @Column(name = "wallet_rule_rate_desc")
    private String walletRuleRateDesc;

    @Column(name = "wallet_rule_rate_from_value")
    private Long walletRuleRateFromValue;

    @Column(name = "wallet_rule_rate_to_value")
    private Long walletRuleRateToValue;

    @Column(name = "wallet_rule_rate_discount")
    private Long walletRuleRateDiscount;

    @ManyToOne
    @JsonIgnoreProperties("walletRules")
    private WalletRule walletRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletRuleRateCode() {
        return walletRuleRateCode;
    }

    public WalletRuleRate walletRuleRateCode(String walletRuleRateCode) {
        this.walletRuleRateCode = walletRuleRateCode;
        return this;
    }

    public void setWalletRuleRateCode(String walletRuleRateCode) {
        this.walletRuleRateCode = walletRuleRateCode;
    }

    public String getWalletRuleRateName() {
        return walletRuleRateName;
    }

    public WalletRuleRate walletRuleRateName(String walletRuleRateName) {
        this.walletRuleRateName = walletRuleRateName;
        return this;
    }

    public void setWalletRuleRateName(String walletRuleRateName) {
        this.walletRuleRateName = walletRuleRateName;
    }

    public String getWalletRuleRateDesc() {
        return walletRuleRateDesc;
    }

    public WalletRuleRate walletRuleRateDesc(String walletRuleRateDesc) {
        this.walletRuleRateDesc = walletRuleRateDesc;
        return this;
    }

    public void setWalletRuleRateDesc(String walletRuleRateDesc) {
        this.walletRuleRateDesc = walletRuleRateDesc;
    }

    public Long getWalletRuleRateFromValue() {
        return walletRuleRateFromValue;
    }

    public WalletRuleRate walletRuleRateFromValue(Long walletRuleRateFromValue) {
        this.walletRuleRateFromValue = walletRuleRateFromValue;
        return this;
    }

    public void setWalletRuleRateFromValue(Long walletRuleRateFromValue) {
        this.walletRuleRateFromValue = walletRuleRateFromValue;
    }

    public Long getWalletRuleRateToValue() {
        return walletRuleRateToValue;
    }

    public WalletRuleRate walletRuleRateToValue(Long walletRuleRateToValue) {
        this.walletRuleRateToValue = walletRuleRateToValue;
        return this;
    }

    public void setWalletRuleRateToValue(Long walletRuleRateToValue) {
        this.walletRuleRateToValue = walletRuleRateToValue;
    }

    public Long getWalletRuleRateDiscount() {
        return walletRuleRateDiscount;
    }

    public WalletRuleRate walletRuleRateDiscount(Long walletRuleRateDiscount) {
        this.walletRuleRateDiscount = walletRuleRateDiscount;
        return this;
    }

    public void setWalletRuleRateDiscount(Long walletRuleRateDiscount) {
        this.walletRuleRateDiscount = walletRuleRateDiscount;
    }

    public WalletRule getWalletRule() {
        return walletRule;
    }

    public WalletRuleRate walletRule(WalletRule walletRule) {
        this.walletRule = walletRule;
        return this;
    }

    public void setWalletRule(WalletRule walletRule) {
        this.walletRule = walletRule;
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
        WalletRuleRate walletRuleRate = (WalletRuleRate) o;
        if (walletRuleRate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletRuleRate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletRuleRate{" +
            "id=" + getId() +
            ", walletRuleRateCode='" + getWalletRuleRateCode() + "'" +
            ", walletRuleRateName='" + getWalletRuleRateName() + "'" +
            ", walletRuleRateDesc='" + getWalletRuleRateDesc() + "'" +
            ", walletRuleRateFromValue=" + getWalletRuleRateFromValue() +
            ", walletRuleRateToValue=" + getWalletRuleRateToValue() +
            ", walletRuleRateDiscount=" + getWalletRuleRateDiscount() +
            "}";
    }
}
