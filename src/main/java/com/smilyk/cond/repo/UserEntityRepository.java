package com.smilyk.cond.repo;

import com.smilyk.cond.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getByUserEmail(String userEmail);
}
