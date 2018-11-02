package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WalletTransaction.
 */
@Entity
@Table(name = "wallet_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wallettransaction")
public class WalletTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "wallet_trans_amount", nullable = false)
    private Long walletTransAmount;

    @NotNull
    @Column(name = "wallet_trans_date", nullable = false)
    private Instant walletTransDate;

    @Column(name = "wallet_trans_ref")
    private String walletTransRef;

    @Column(name = "wallet_trans_username")
    private String walletTransUsername;

    @Column(name = "wallet_trans_udf_1")
    private String walletTransUDF1;

    @Column(name = "wallet_trans_udf_2")
    private String walletTransUDF2;

    @Column(name = "wallet_trans_udf_3")
    private String walletTransUDF3;

    @ManyToOne
    @JsonIgnoreProperties("walletTransTypes")
    private WalletTransactionType walletTransactionType;

    @ManyToOne
    @JsonIgnoreProperties("wallets")
    private Wallet wallet;

    @OneToMany(mappedBy = "walletTransaction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartnerTransaction> walletTransactions = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWalletTransAmount() {
        return walletTransAmount;
    }

    public WalletTransaction walletTransAmount(Long walletTransAmount) {
        this.walletTransAmount = walletTransAmount;
        return this;
    }

    public void setWalletTransAmount(Long walletTransAmount) {
        this.walletTransAmount = walletTransAmount;
    }

    public Instant getWalletTransDate() {
        return walletTransDate;
    }

    public WalletTransaction walletTransDate(Instant walletTransDate) {
        this.walletTransDate = walletTransDate;
        return this;
    }

    public void setWalletTransDate(Instant walletTransDate) {
        this.walletTransDate = walletTransDate;
    }

    public String getWalletTransRef() {
        return walletTransRef;
    }

    public WalletTransaction walletTransRef(String walletTransRef) {
        this.walletTransRef = walletTransRef;
        return this;
    }

    public void setWalletTransRef(String walletTransRef) {
        this.walletTransRef = walletTransRef;
    }

    public String getWalletTransUsername() {
        return walletTransUsername;
    }

    public WalletTransaction walletTransUsername(String walletTransUsername) {
        this.walletTransUsername = walletTransUsername;
        return this;
    }

    public void setWalletTransUsername(String walletTransUsername) {
        this.walletTransUsername = walletTransUsername;
    }

    public String getWalletTransUDF1() {
        return walletTransUDF1;
    }

    public WalletTransaction walletTransUDF1(String walletTransUDF1) {
        this.walletTransUDF1 = walletTransUDF1;
        return this;
    }

    public void setWalletTransUDF1(String walletTransUDF1) {
        this.walletTransUDF1 = walletTransUDF1;
    }

    public String getWalletTransUDF2() {
        return walletTransUDF2;
    }

    public WalletTransaction walletTransUDF2(String walletTransUDF2) {
        this.walletTransUDF2 = walletTransUDF2;
        return this;
    }

    public void setWalletTransUDF2(String walletTransUDF2) {
        this.walletTransUDF2 = walletTransUDF2;
    }

    public String getWalletTransUDF3() {
        return walletTransUDF3;
    }

    public WalletTransaction walletTransUDF3(String walletTransUDF3) {
        this.walletTransUDF3 = walletTransUDF3;
        return this;
    }

    public void setWalletTransUDF3(String walletTransUDF3) {
        this.walletTransUDF3 = walletTransUDF3;
    }

    public WalletTransactionType getWalletTransactionType() {
        return walletTransactionType;
    }

    public WalletTransaction walletTransactionType(WalletTransactionType walletTransactionType) {
        this.walletTransactionType = walletTransactionType;
        return this;
    }

    public void setWalletTransactionType(WalletTransactionType walletTransactionType) {
        this.walletTransactionType = walletTransactionType;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public WalletTransaction wallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Set<PartnerTransaction> getWalletTransactions() {
        return walletTransactions;
    }

    public WalletTransaction walletTransactions(Set<PartnerTransaction> partnerTransactions) {
        this.walletTransactions = partnerTransactions;
        return this;
    }

    public WalletTransaction addWalletTransaction(PartnerTransaction partnerTransaction) {
        this.walletTransactions.add(partnerTransaction);
        partnerTransaction.setWalletTransaction(this);
        return this;
    }

    public WalletTransaction removeWalletTransaction(PartnerTransaction partnerTransaction) {
        this.walletTransactions.remove(partnerTransaction);
        partnerTransaction.setWalletTransaction(null);
        return this;
    }

    public void setWalletTransactions(Set<PartnerTransaction> partnerTransactions) {
        this.walletTransactions = partnerTransactions;
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
        WalletTransaction walletTransaction = (WalletTransaction) o;
        if (walletTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletTransaction{" +
            "id=" + getId() +
            ", walletTransAmount=" + getWalletTransAmount() +
            ", walletTransDate='" + getWalletTransDate() + "'" +
            ", walletTransRef='" + getWalletTransRef() + "'" +
            ", walletTransUsername='" + getWalletTransUsername() + "'" +
            ", walletTransUDF1='" + getWalletTransUDF1() + "'" +
            ", walletTransUDF2='" + getWalletTransUDF2() + "'" +
            ", walletTransUDF3='" + getWalletTransUDF3() + "'" +
            "}";
    }
}
