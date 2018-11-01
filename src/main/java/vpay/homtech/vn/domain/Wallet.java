package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Wallet.
 */
@Entity
@Table(name = "wallet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "wallet_number", nullable = false)
    private String walletNumber;

    @NotNull
    @Column(name = "wallet_is_active", nullable = false)
    private Boolean walletIsActive;

    @Column(name = "wallet_desc")
    private String walletDesc;

    @ManyToOne
    @JsonIgnoreProperties("walletTypes")
    private WalletType walletType;

    @ManyToOne
    @JsonIgnoreProperties("payPartners")
    private PayPartner payPartner;

    @OneToMany(mappedBy = "wallet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletAllotment> wallets = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public Wallet walletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
        return this;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public Boolean isWalletIsActive() {
        return walletIsActive;
    }

    public Wallet walletIsActive(Boolean walletIsActive) {
        this.walletIsActive = walletIsActive;
        return this;
    }

    public void setWalletIsActive(Boolean walletIsActive) {
        this.walletIsActive = walletIsActive;
    }

    public String getWalletDesc() {
        return walletDesc;
    }

    public Wallet walletDesc(String walletDesc) {
        this.walletDesc = walletDesc;
        return this;
    }

    public void setWalletDesc(String walletDesc) {
        this.walletDesc = walletDesc;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public Wallet walletType(WalletType walletType) {
        this.walletType = walletType;
        return this;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public PayPartner getPayPartner() {
        return payPartner;
    }

    public Wallet payPartner(PayPartner payPartner) {
        this.payPartner = payPartner;
        return this;
    }

    public void setPayPartner(PayPartner payPartner) {
        this.payPartner = payPartner;
    }

    public Set<WalletAllotment> getWallets() {
        return wallets;
    }

    public Wallet wallets(Set<WalletAllotment> walletAllotments) {
        this.wallets = walletAllotments;
        return this;
    }

    public Wallet addWallet(WalletAllotment walletAllotment) {
        this.wallets.add(walletAllotment);
        walletAllotment.setWallet(this);
        return this;
    }

    public Wallet removeWallet(WalletAllotment walletAllotment) {
        this.wallets.remove(walletAllotment);
        walletAllotment.setWallet(null);
        return this;
    }

    public void setWallets(Set<WalletAllotment> walletAllotments) {
        this.wallets = walletAllotments;
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
        Wallet wallet = (Wallet) o;
        if (wallet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wallet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wallet{" +
            "id=" + getId() +
            ", walletNumber='" + getWalletNumber() + "'" +
            ", walletIsActive='" + isWalletIsActive() + "'" +
            ", walletDesc='" + getWalletDesc() + "'" +
            "}";
    }
}
