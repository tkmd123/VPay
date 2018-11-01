package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WalletAllotment.
 */
@Entity
@Table(name = "wallet_allotment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "walletallotment")
public class WalletAllotment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_allotment_amount")
    private Long walletAllotmentAmount;

    @ManyToOne
    @JsonIgnoreProperties("partners")
    private Partner partner;

    @ManyToOne
    @JsonIgnoreProperties("wallets")
    private Wallet wallet;

    @OneToMany(mappedBy = "walletAllotment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletTransaction> walletAllotments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWalletAllotmentAmount() {
        return walletAllotmentAmount;
    }

    public WalletAllotment walletAllotmentAmount(Long walletAllotmentAmount) {
        this.walletAllotmentAmount = walletAllotmentAmount;
        return this;
    }

    public void setWalletAllotmentAmount(Long walletAllotmentAmount) {
        this.walletAllotmentAmount = walletAllotmentAmount;
    }

    public Partner getPartner() {
        return partner;
    }

    public WalletAllotment partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public WalletAllotment wallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Set<WalletTransaction> getWalletAllotments() {
        return walletAllotments;
    }

    public WalletAllotment walletAllotments(Set<WalletTransaction> walletTransactions) {
        this.walletAllotments = walletTransactions;
        return this;
    }

    public WalletAllotment addWalletAllotment(WalletTransaction walletTransaction) {
        this.walletAllotments.add(walletTransaction);
        walletTransaction.setWalletAllotment(this);
        return this;
    }

    public WalletAllotment removeWalletAllotment(WalletTransaction walletTransaction) {
        this.walletAllotments.remove(walletTransaction);
        walletTransaction.setWalletAllotment(null);
        return this;
    }

    public void setWalletAllotments(Set<WalletTransaction> walletTransactions) {
        this.walletAllotments = walletTransactions;
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
        WalletAllotment walletAllotment = (WalletAllotment) o;
        if (walletAllotment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), walletAllotment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WalletAllotment{" +
            "id=" + getId() +
            ", walletAllotmentAmount=" + getWalletAllotmentAmount() +
            "}";
    }
}
