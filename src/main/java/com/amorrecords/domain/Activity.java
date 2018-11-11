package com.amorrecords.domain;

import com.amorrecords.domain.enumeration.Operation;
import com.amorrecords.domain.enumeration.TypePayment;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    /*@Column(name = "due_date")
    private Instant dueDate;*/

    @Column(name = "reference")
    private String reference;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "amount_unit")
    private Double amountUnit;

    @Column(name = "amount_total")
    private Double amountTotal;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private TypePayment paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    private Operation operation;

/*    @OneToOne
    @JoinColumn(unique = true)
    private Supplier supplier;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Activity date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

/*    public Instant getDueDate() {
        return dueDate;
    }

    public Activity dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }*/

    public String getReference() {
        return reference;
    }

    public Activity reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public Activity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Activity quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmountUnit() {
        return amountUnit;
    }

    public Activity amountUnit(Double amountUnit) {
        this.amountUnit = amountUnit;
        return this;
    }

    public void setAmountUnit(Double amountUnit) {
        this.amountUnit = amountUnit;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public Activity amountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
        return this;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Activity invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public TypePayment getPaymentType() {
        return paymentType;
    }

    public Activity paymentType(TypePayment paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(TypePayment paymentType) {
        this.paymentType = paymentType;
    }

    public Operation getOperation() {
        return operation;
    }

    public Activity operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

/*    public Supplier getSupplier() {
        return supplier;
    }

    public Activity supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Activity products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Activity addProduct(Product product) {
        this.products.add(product);
        product.setActivity(this);
        return this;
    }*/

/*    public Activity removeProduct(Product product) {
        this.products.remove(product);
        product.setActivity(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }*/
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            /*", dueDate='" + getDueDate() + "'" +*/
            ", reference='" + getReference() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", amountUnit=" + getAmountUnit() +
            ", amountTotal=" + getAmountTotal() +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", operation='" + getOperation() + "'" +
            "}";
    }
}
