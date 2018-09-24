package com.amorrecords.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private Disco disco;

    @OneToOne
    @JoinColumn(unique = true)
    private Bar bar;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private Supplier supplier;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Disco getDisco() {
        return disco;
    }

    public Product disco(Disco disco) {
        this.disco = disco;
        return this;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public Bar getBar() {
        return bar;
    }

    public Product bar(Bar bar) {
        this.bar = bar;
        return this;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Activity getActivity() {
        return activity;
    }

    public Product activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Product supplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
            ", name='" + getName() + "'" +
            "}";
    }
}
