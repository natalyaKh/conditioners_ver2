package com.smilyk.cond.service.valid;

import com.smilyk.cond.model.UserEntity;

public interface ValidationService {
    void checkUniqueUser(String userEmail);

    UserEntity checkIfUserExists(String userUuid);
}
