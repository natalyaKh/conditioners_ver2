package com.smilyk.cond.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.Collection;

/**
 * UserEntity class extends BaseEntity
 */
@Builder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Column(nullable = false)
    private String uuidUser;

    @Column(nullable = false)
    private String firstName;

    private String secondName;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String password;
    /** now all users has confirmed email. We dont need this functionality
     * in this project
     */
    @Column(nullable = false)
    Boolean deleted = false;

    public UserEntity(String uuidUser, String firstName, String secondName, String userEmail, String password, Boolean deleted,
                      Boolean confirmEmail, Boolean blocked, Collection<RoleEntity> roles) {
        this.uuidUser = uuidUser;
        this.firstName = firstName;
        this.secondName = secondName;
        this.userEmail = userEmail;
        this.password = password;
        this.deleted = deleted;
        this.confirmEmail = confirmEmail;
        this.blocked = blocked;
        this.roles = roles;
    }

    @Column(nullable = false)
    private Boolean confirmEmail = false;

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Column(nullable = false)
    private Boolean blocked = false;

    @JoinTable(name = "users_roles", joinColumns = @JoinColumn
        (name = "users_id", referencedColumnName = "id"))
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles;

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "uuidUser='" + uuidUser + '\'' +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            ", userEmail='" + userEmail + '\'' +
            ", password='" + password + '\'' +
            ", deleted=" + deleted +
            ", confirmEmail=" + confirmEmail +
            ", roles=" + roles +
            '}';
    }

    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(Boolean confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public UserEntity() {
    }

}
