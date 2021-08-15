package com.smilyk.repo;

import com.smilyk.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User Entity Repository
 */
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getByUserEmail(String userEmail);

    Optional<UserEntity> getUserByUuidUserAndDeletedAndBlocked(String userUuid,
                                                           boolean deleted, boolean blocked);

    Optional<Object> findUserByUuidUserAndDeletedAndBlocked(String userUuid, boolean b, boolean b1);

    UserEntity findUserByUserEmail(String email);
}
