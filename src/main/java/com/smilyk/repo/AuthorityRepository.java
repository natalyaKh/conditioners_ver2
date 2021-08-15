package com.smilyk.repo;

import com.smilyk.model.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Authority Repository
 */
public interface AuthorityRepository  extends JpaRepository<AuthorityEntity, Long> {

    AuthorityEntity findByName(String name);
}
