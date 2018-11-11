
package com.amorrecords.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


/**
 * A Table_bar.
 */

@Entity
@Table(name = "table_bar")
public class TableBar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column
    private LocalDate date;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="table_bar_id")
    //@JsonIgnore
    private List<Activity> activities = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public TableBar activities(List<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public TableBar addActivity(Activity activity) {
        this.activities.add(activity);
        //activity.setTable_bar(this);
        return this;
    }

    public TableBar removeActivity(Activity activity) {
        this.activities.remove(activity);
        //activity.setTable_bar(null);
        return this;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
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
        TableBar table_bar = (TableBar) o;
        if (table_bar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), table_bar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TableBar{" +
            "id=" + getId() +
            "}";
    }
}

