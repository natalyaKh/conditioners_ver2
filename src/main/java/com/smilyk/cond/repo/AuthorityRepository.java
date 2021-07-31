package com.smilyk.cond.repo;

import com.smilyk.cond.model.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository  extends JpaRepository<AuthorityEntity, Long> {

    AuthorityEntity findByName(String name);
}
