package com.smilyk.cond.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Table(name = "authorities")
@Entity
public class AuthorityEntity extends BaseEntity{
    @Column(nullable = false, length = 20)
    String name;
    @ManyToMany(mappedBy="authorities")
    private Collection<RoleEntity> roles;

    public AuthorityEntity(String name) {
        this.name = name;
    }

    public AuthorityEntity(AuthorityEntity authorityEntity) {
    }

    public AuthorityEntity() {
    }

    @Override
    public String toString() {
        return "AuthorityEntity{" +
            "name='" + name + '\'' +
            ", roles=" + roles +
            '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

    public AuthorityEntity(String name, Collection<RoleEntity> roles) {
        this.name = name;
        this.roles = roles;
    }
}
