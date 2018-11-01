package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties("walletTransTypes")
    private WalletTransctionType walletTransctionType;

    @ManyToOne
    @JsonIgnoreProperties("walletAllotments")
    private WalletAllotment walletAllotment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WalletTransctionType getWalletTransctionType() {
        return walletTransctionType;
    }

    public WalletTransaction walletTransctionType(WalletTransctionType walletTransctionType) {
        this.walletTransctionType = walletTransctionType;
        return this;
    }

    public void setWalletTransctionType(WalletTransctionType walletTransctionType) {
        this.walletTransctionType = walletTransctionType;
    }

    public WalletAllotment getWalletAllotment() {
        return walletAllotment;
    }

    public WalletTransaction walletAllotment(WalletAllotment walletAllotment) {
        this.walletAllotment = walletAllotment;
        return this;
    }

    public void setWalletAllotment(WalletAllotment walletAllotment) {
        this.walletAllotment = walletAllotment;
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
            "}";
    }
}
