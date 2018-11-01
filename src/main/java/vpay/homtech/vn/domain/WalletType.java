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
 * A WalletType.
 */
@Entity
@Table(name = "wallet_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wallettype")
public class WalletType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_type_code")
    private String walletTypeCode;

    @Column(name = "wallet_type_name")
    private String walletTypeName;

    @Column(name = "wallet_type_desc")
    private String walletTypeDesc;

    @OneToMany(mappedBy = "walletType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wallet> walletTypes = new HashSet<>();
    @OneToMany(mappedBy = "walletType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletRule> walletTypes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletTypeCode() {
        return walletTypeCode;
    }

    public WalletType walletTypeCode(String walletTypeCode) {
        this.walletTypeCode = walletTypeCode;
        return this;
    }

    public void setWalletTypeCode(String walletTypeCode) {
        this.walletTypeCode = walletTypeCode;
    }

    public String getWalletTypeName() {
        return walletTypeName;
    }

    public WalletType walletTypeName(String walletTypeName) {
        this.walletTypeName = walletTypeName;
        return this;
    }

    public void setWalletTypeName(String walletTypeName) {
        this.walletTypeName = walletTypeName;
    }

    public String getWalletTypeDesc() {
        return walletTypeDesc;
    }

    public WalletType walletTypeDesc(String walletTypeDesc) {
        this.walletTypeDesc = walletTypeDesc;
        return this;
    }

    public void setWalletTypeDesc(String walletTypeDesc) {
        this.walletTypeDesc = walletTypeDesc;
    }

    public Set<Wallet> getWalletTypes() {
        return walletTypes;
    }

    public WalletType walletTypes(Set<Wallet> wallets) {
        this.walletTypes = wallets;
        return this;
    }

    public WalletType addWalletType(Wallet wallet) {
        this.walletTypes.add(wallet);
        wallet.setWalletType(this);
        return this;
    }

    public WalletType removeWalletType(Wallet wallet) {
        this.walletTypes.remove(wallet);
        wallet.setWalletType(null);
        return this;
    }

    public void setWalletTypes(Set<Wallet> wallets) {
        this.walletTypes = wallets;
    }

    public Set<WalletRule> getWalletTypes() {
        return walletTypes;
    }

    public WalletType walletTypes(Set<WalletRule> walletRules) {
        this.walletTypes = walletRules;
        return this;
    }

    public WalletType addWalletType(WalletRule walletRule) {
        this.walletTypes.add(walletRule);
        walletRule.setWalletType(this);
        return this;
    }

    public WalletType removeWalletType(WalletRule walletRule) {
        this.walletTypes.remove(walletRule);
        walletRule.setWalletType(null);
        return this;
    }

    public void setWalletTypes(Set<WalletRule> walletRules) {
        this.walletTypes = walletRules;
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
        WalletType walletType = (WalletType) o;
        if (walletType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletType{" +
            "id=" + getId() +
            ", walletTypeCode='" + getWalletTypeCode() + "'" +
            ", walletTypeName='" + getWalletTypeName() + "'" +
            ", walletTypeDesc='" + getWalletTypeDesc() + "'" +
            "}";
    }
}
