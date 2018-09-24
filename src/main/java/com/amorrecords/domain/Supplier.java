package com.amorrecords.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contry")
    private String contry;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "site")
    private String site;

    @Column(name = "date_purchase")
    private Instant datePurchase;

    @Column(name = "contact")
    private String contact;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Supplier name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContry() {
        return contry;
    }

    public Supplier contry(String contry) {
        this.contry = contry;
        return this;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getEmail() {
        return email;
    }

    public Supplier email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Supplier phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public Supplier site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Instant getDatePurchase() {
        return datePurchase;
    }

    public Supplier datePurchase(Instant datePurchase) {
        this.datePurchase = datePurchase;
        return this;
    }

    public void setDatePurchase(Instant datePurchase) {
        this.datePurchase = datePurchase;
    }

    public String getContact() {
        return contact;
    }

    public Supplier contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Supplier products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Supplier addProduct(Product product) {
        this.products.add(product);
        product.setSupplier(this);
        return this;
    }

    public Supplier removeProduct(Product product) {
        this.products.remove(product);
        product.setSupplier(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
        Supplier supplier = (Supplier) o;
        if (supplier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Supplier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contry='" + getContry() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", site='" + getSite() + "'" +
            ", datePurchase='" + getDatePurchase() + "'" +
            ", contact='" + getContact() + "'" +
            "}";
    }
}
