package com.smilyk.cond;

import com.smilyk.cond.enums.Authorities;
import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.AuthorityEntity;
import com.smilyk.cond.model.RoleEntity;
import com.smilyk.cond.repo.AuthorityRepository;
import com.smilyk.cond.repo.RoleRepository;
import com.smilyk.cond.repo.UserEntityRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

/**
 * This is setup class.
 * Class crate roles and authorities for users
 * By default in appl uses:
 * adminRole - authorities read, write, delete
 * managerRole - authorities read, write(for some methods)
 * workerRole - authorities read(for some methods), write (for some methods)
 */
@Component
public class InitialRolesAuthoritiesSetup {
    final AuthorityRepository authorityRepository;
    final RoleRepository roleRepository;
    final UserEntityRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @param authorityRepository
     * @param roleRepository
     * @param userRepository
     * @param bCryptPasswordEncoder
     */
    public InitialRolesAuthoritiesSetup(AuthorityRepository authorityRepository, RoleRepository roleRepository,
                                        UserEntityRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * method that wait for event and create default roles with list of authorities
     * @param event
     */
    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
/*
 * creating authorities
 */
        AuthorityEntity readAuthority = createAuthority(Authorities.READ.name());
        AuthorityEntity writeAuthority = createAuthority(Authorities.WRITE.name());
        AuthorityEntity deleteAuthority = createAuthority(Authorities.DELETE.name());


        RoleEntity workerRole = createRole(Roles.ROLE_WORKER.name(), Arrays.asList(
            readAuthority));
        RoleEntity adminRole = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority,
            writeAuthority, deleteAuthority));
        RoleEntity managerRole = createRole(Roles.ROLE_MANAGER.name(), Arrays.asList(readAuthority,
            writeAuthority));
    }

    /**
     * method that create default Role
     * @param name
     * @param authorityEntities
     * @return
     */
    public RoleEntity createRole(String name, Collection<AuthorityEntity> authorityEntities) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(name);
            roleEntity.setAuthorities(authorityEntities);
            roleRepository.save(roleEntity);
        }
        return roleEntity;

    }

    /**
     * method that create default Authority
     * @param name
     * @return
     */
    public AuthorityEntity createAuthority(String name) {
        AuthorityEntity authorityEntity = authorityRepository.findByName(name);
        if (authorityEntity == null) {
            authorityEntity = new AuthorityEntity(name);
            authorityRepository.save(authorityEntity);

        }
        return authorityEntity;
    }


}
