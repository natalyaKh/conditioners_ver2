package com.smilyk.cond.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * RoleEntity class extends BaseEntity
 */
@Table(name = "roles")
@Entity
public class RoleEntity extends BaseEntity {
    @Column(nullable = false, length = 20)
    String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> userEntity;
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
        joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity(String name, Collection<UserEntity> userEntity, Collection<AuthorityEntity> authorities) {
        this.name = name;
        this.userEntity = userEntity;
        this.authorities = authorities;
    }

    public RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(Collection<UserEntity> userEntity) {
        this.userEntity = userEntity;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
