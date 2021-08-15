package com.smilyk.service.valid;

import com.smilyk.model.UserEntity;

/**
 * Service for Validation. This is interface, his impl -> {@link ValidationServiceImpl}
 */
public interface ValidationService {
    void checkUniqueUser(String userEmail);

    UserEntity checkIfUserExists(String userUuid);
}
