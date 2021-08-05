package com.smilyk.cond.repo;

import com.smilyk.cond.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role Repository
 */
@Repository
public interface RoleRepository  extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
