package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
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
 * ProductType: VN, VT, MB, VTC, ...
 */
@ApiModel(description = "ProductType: VN, VT, MB, VTC, ...")
@Entity
@Table(name = "product_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "producttype")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_type_code", nullable = false)
    private String productTypeCode;

    @NotNull
    @Column(name = "product_type_name", nullable = false)
    private String productTypeName;

    @Column(name = "product_type_desc")
    private String productTypeDesc;

    @Column(name = "product_udf_1")
    private String productUDF1;

    @Column(name = "product_udf_2")
    private String productUDF2;

    @Column(name = "product_udf_3")
    private String productUDF3;

    @OneToMany(mappedBy = "productType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> productTypes = new HashSet<>();
    @OneToMany(mappedBy = "productType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wallet> productTypes = new HashSet<>();
    @OneToMany(mappedBy = "productType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WalletRule> productTypes = new HashSet<>();
    @OneToMany(mappedBy = "productType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PartnerTransaction> productTypes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public ProductType productTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
        return this;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public ProductType productTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
        return this;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeDesc() {
        return productTypeDesc;
    }

    public ProductType productTypeDesc(String productTypeDesc) {
        this.productTypeDesc = productTypeDesc;
        return this;
    }

    public void setProductTypeDesc(String productTypeDesc) {
        this.productTypeDesc = productTypeDesc;
    }

    public String getProductUDF1() {
        return productUDF1;
    }

    public ProductType productUDF1(String productUDF1) {
        this.productUDF1 = productUDF1;
        return this;
    }

    public void setProductUDF1(String productUDF1) {
        this.productUDF1 = productUDF1;
    }

    public String getProductUDF2() {
        return productUDF2;
    }

    public ProductType productUDF2(String productUDF2) {
        this.productUDF2 = productUDF2;
        return this;
    }

    public void setProductUDF2(String productUDF2) {
        this.productUDF2 = productUDF2;
    }

    public String getProductUDF3() {
        return productUDF3;
    }

    public ProductType productUDF3(String productUDF3) {
        this.productUDF3 = productUDF3;
        return this;
    }

    public void setProductUDF3(String productUDF3) {
        this.productUDF3 = productUDF3;
    }

    public Set<Product> getProductTypes() {
        return productTypes;
    }

    public ProductType productTypes(Set<Product> products) {
        this.productTypes = products;
        return this;
    }

    public ProductType addProductType(Product product) {
        this.productTypes.add(product);
        product.setProductType(this);
        return this;
    }

    public ProductType removeProductType(Product product) {
        this.productTypes.remove(product);
        product.setProductType(null);
        return this;
    }

    public void setProductTypes(Set<Product> products) {
        this.productTypes = products;
    }

    public Set<Wallet> getProductTypes() {
        return productTypes;
    }

    public ProductType productTypes(Set<Wallet> wallets) {
        this.productTypes = wallets;
        return this;
    }

    public ProductType addProductType(Wallet wallet) {
        this.productTypes.add(wallet);
        wallet.setProductType(this);
        return this;
    }

    public ProductType removeProductType(Wallet wallet) {
        this.productTypes.remove(wallet);
        wallet.setProductType(null);
        return this;
    }

    public void setProductTypes(Set<Wallet> wallets) {
        this.productTypes = wallets;
    }

    public Set<WalletRule> getProductTypes() {
        return productTypes;
    }

    public ProductType productTypes(Set<WalletRule> walletRules) {
        this.productTypes = walletRules;
        return this;
    }

    public ProductType addProductType(WalletRule walletRule) {
        this.productTypes.add(walletRule);
        walletRule.setProductType(this);
        return this;
    }

    public ProductType removeProductType(WalletRule walletRule) {
        this.productTypes.remove(walletRule);
        walletRule.setProductType(null);
        return this;
    }

    public void setProductTypes(Set<WalletRule> walletRules) {
        this.productTypes = walletRules;
    }

    public Set<PartnerTransaction> getProductTypes() {
        return productTypes;
    }

    public ProductType productTypes(Set<PartnerTransaction> partnerTransactions) {
        this.productTypes = partnerTransactions;
        return this;
    }

    public ProductType addProductType(PartnerTransaction partnerTransaction) {
        this.productTypes.add(partnerTransaction);
        partnerTransaction.setProductType(this);
        return this;
    }

    public ProductType removeProductType(PartnerTransaction partnerTransaction) {
        this.productTypes.remove(partnerTransaction);
        partnerTransaction.setProductType(null);
        return this;
    }

    public void setProductTypes(Set<PartnerTransaction> partnerTransactions) {
        this.productTypes = partnerTransactions;
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
        ProductType productType = (ProductType) o;
        if (productType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductType{" +
            "id=" + getId() +
            ", productTypeCode='" + getProductTypeCode() + "'" +
            ", productTypeName='" + getProductTypeName() + "'" +
            ", productTypeDesc='" + getProductTypeDesc() + "'" +
            ", productUDF1='" + getProductUDF1() + "'" +
            ", productUDF2='" + getProductUDF2() + "'" +
            ", productUDF3='" + getProductUDF3() + "'" +
            "}";
    }
}
