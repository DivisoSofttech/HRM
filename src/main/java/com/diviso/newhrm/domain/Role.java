package com.diviso.newhrm.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private Long reference;

    @ManyToMany
    @JoinTable(name = "role_peoples",
               joinColumns = @JoinColumn(name="roles_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="peoples_id", referencedColumnName="id"))
    private Set<Peoples> peoples = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return reference;
    }

    public Role reference(Long reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Set<Peoples> getPeoples() {
        return peoples;
    }

    public Role peoples(Set<Peoples> peoples) {
        this.peoples = peoples;
        return this;
    }

    public Role addPeoples(Peoples peoples) {
        this.peoples.add(peoples);
        return this;
    }

    public Role removePeoples(Peoples peoples) {
        this.peoples.remove(peoples);
        return this;
    }

    public void setPeoples(Set<Peoples> peoples) {
        this.peoples = peoples;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", reference=" + getReference() +
            "}";
    }
}
