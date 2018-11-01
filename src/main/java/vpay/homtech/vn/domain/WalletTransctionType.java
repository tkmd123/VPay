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
 * A WalletTransctionType.
 */
@Entity
@Table(name = "wallet_transction_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wallettransctiontype")
public class WalletTransctionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_trans_type_code")
    private String walletTransTypeCode;

    @Column(name = "wallet_trans_type_name")
    private String walletTransTypeName;

    @Column(name = "wallet_trans_type_desc")
    private String walletTransTypeDesc;

    @NotNull
    @Column(name = "wallet_trans_type_flag", nullable = false)
    private Integer walletTransTypeFlag;

    @OneToMany(mappedBy = "walletTransctionType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletTransaction> walletTransTypes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletTransTypeCode() {
        return walletTransTypeCode;
    }

    public WalletTransctionType walletTransTypeCode(String walletTransTypeCode) {
        this.walletTransTypeCode = walletTransTypeCode;
        return this;
    }

    public void setWalletTransTypeCode(String walletTransTypeCode) {
        this.walletTransTypeCode = walletTransTypeCode;
    }

    public String getWalletTransTypeName() {
        return walletTransTypeName;
    }

    public WalletTransctionType walletTransTypeName(String walletTransTypeName) {
        this.walletTransTypeName = walletTransTypeName;
        return this;
    }

    public void setWalletTransTypeName(String walletTransTypeName) {
        this.walletTransTypeName = walletTransTypeName;
    }

    public String getWalletTransTypeDesc() {
        return walletTransTypeDesc;
    }

    public WalletTransctionType walletTransTypeDesc(String walletTransTypeDesc) {
        this.walletTransTypeDesc = walletTransTypeDesc;
        return this;
    }

    public void setWalletTransTypeDesc(String walletTransTypeDesc) {
        this.walletTransTypeDesc = walletTransTypeDesc;
    }

    public Integer getWalletTransTypeFlag() {
        return walletTransTypeFlag;
    }

    public WalletTransctionType walletTransTypeFlag(Integer walletTransTypeFlag) {
        this.walletTransTypeFlag = walletTransTypeFlag;
        return this;
    }

    public void setWalletTransTypeFlag(Integer walletTransTypeFlag) {
        this.walletTransTypeFlag = walletTransTypeFlag;
    }

    public Set<WalletTransaction> getWalletTransTypes() {
        return walletTransTypes;
    }

    public WalletTransctionType walletTransTypes(Set<WalletTransaction> walletTransactions) {
        this.walletTransTypes = walletTransactions;
        return this;
    }

    public WalletTransctionType addWalletTransType(WalletTransaction walletTransaction) {
        this.walletTransTypes.add(walletTransaction);
        walletTransaction.setWalletTransctionType(this);
        return this;
    }

    public WalletTransctionType removeWalletTransType(WalletTransaction walletTransaction) {
        this.walletTransTypes.remove(walletTransaction);
        walletTransaction.setWalletTransctionType(null);
        return this;
    }

    public void setWalletTransTypes(Set<WalletTransaction> walletTransactions) {
        this.walletTransTypes = walletTransactions;
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
        WalletTransctionType walletTransctionType = (WalletTransctionType) o;
        if (walletTransctionType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletTransctionType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletTransctionType{" +
            "id=" + getId() +
            ", walletTransTypeCode='" + getWalletTransTypeCode() + "'" +
            ", walletTransTypeName='" + getWalletTransTypeName() + "'" +
            ", walletTransTypeDesc='" + getWalletTransTypeDesc() + "'" +
            ", walletTransTypeFlag=" + getWalletTransTypeFlag() +
            "}";
    }
}
