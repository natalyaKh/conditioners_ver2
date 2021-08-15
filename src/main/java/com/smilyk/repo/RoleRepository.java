package com.smilyk.repo;

import com.smilyk.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role Repository
 */
@Repository
public interface RoleRepository  extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
