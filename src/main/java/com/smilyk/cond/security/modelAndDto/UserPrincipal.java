package com.smilyk.cond.security.modelAndDto;

import com.smilyk.cond.model.AuthorityEntity;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.model.UserEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private UserEntity user;

    public UserPrincipal(UserEntity user) {
        this.user = user;
    }

    public UserPrincipal() {
    }

    /**
     * method, that got all privileges of user from DB
     *
     * @return list of {@link GrantedAuthority}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        Collection<RoleEntity> roles = user.getRoles();
        if (roles == null) return authorities;
        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntities.addAll(role.getAuthorities());
        });
        authorityEntities.forEach(authorityEntity -> authorities.add(
            new SimpleGrantedAuthority(authorityEntity.getName())));
        return authorities;
    }

    /**
     * @return password of {@link UserEntity}
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * @return name of {@link UserEntity}
     */
    @Override
    public String getUsername() {
        return this.user.getUserEmail();
    }

    /**
     * @return if account of {@link UserEntity} expired
     * if yes - access denied
     */
    @Override
    public boolean isAccountNonExpired() {
        return !this.user.getDeleted();
    }

    /**
     * @return if account of {@link UserEntity} blocked
     * if yes - access denied
     */
    @Override
    public boolean isAccountNonLocked() {
        return !this.user.getBlocked();
    }

    /**
     * @return true by HardCode.
     * if you need logic po - you should write it
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return if user confirmed his account.
     * if not - access denied
     */
    @Override
    public boolean isEnabled() {

        return this.user.getConfirmEmail();
    }
}
