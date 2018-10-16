package com.amorrecords.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Table_bar.
 */
@Entity
@Table(name = "table_bar")
public class Table_bar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "table_bar")
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Table_bar activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Table_bar addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setTable_bar(this);
        return this;
    }

    public Table_bar removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setTable_bar(null);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
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
        Table_bar table_bar = (Table_bar) o;
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
        return "Table_bar{" +
            "id=" + getId() +
            "}";
    }
}
