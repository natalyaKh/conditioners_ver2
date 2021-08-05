package com.smilyk.cond.repo;

import com.smilyk.cond.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Role Repository
 */
public interface RoleRepository  extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
