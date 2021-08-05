package com.smilyk.cond.service.valid;

import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.service.admin.AdminUserServiceImpl;

/**
 * Service for Validation. This is interface, his impl -> {@link ValidationServiceImpl}
 */
public interface ValidationService {
    void checkUniqueUser(String userEmail);

    UserEntity checkIfUserExists(String userUuid);
}
