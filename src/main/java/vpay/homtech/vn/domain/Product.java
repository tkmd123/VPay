package vpay.homtech.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * Product: VN: 50k, VN: 100k
 * VT: 50k, VT: 100k
 */
@ApiModel(description = "Product: VN: 50k, VN: 100k VT: 50k, VT: 100k")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_code", nullable = false)
    private String productCode;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "product_udf_1")
    private String productUDF1;

    @Column(name = "product_udf_2")
    private String productUDF2;

    @Column(name = "product_udf_3")
    private String productUDF3;

    @ManyToOne
    @JsonIgnoreProperties("productTypes")
    private ProductType productType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public Product productCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Product productDesc(String productDesc) {
        this.productDesc = productDesc;
        return this;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductUDF1() {
        return productUDF1;
    }

    public Product productUDF1(String productUDF1) {
        this.productUDF1 = productUDF1;
        return this;
    }

    public void setProductUDF1(String productUDF1) {
        this.productUDF1 = productUDF1;
    }

    public String getProductUDF2() {
        return productUDF2;
    }

    public Product productUDF2(String productUDF2) {
        this.productUDF2 = productUDF2;
        return this;
    }

    public void setProductUDF2(String productUDF2) {
        this.productUDF2 = productUDF2;
    }

    public String getProductUDF3() {
        return productUDF3;
    }

    public Product productUDF3(String productUDF3) {
        this.productUDF3 = productUDF3;
        return this;
    }

    public void setProductUDF3(String productUDF3) {
        this.productUDF3 = productUDF3;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productCode='" + getProductCode() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productDesc='" + getProductDesc() + "'" +
            ", productUDF1='" + getProductUDF1() + "'" +
            ", productUDF2='" + getProductUDF2() + "'" +
            ", productUDF3='" + getProductUDF3() + "'" +
            "}";
    }
}
