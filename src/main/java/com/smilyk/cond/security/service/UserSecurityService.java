package com.smilyk.cond.security.service;

import com.smilyk.cond.constants.LoggerConstants;
import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.UserEntityRepository;
import com.smilyk.cond.security.modelAndDto.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserSecurityInterface {
    @Autowired
    UserEntityRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(LoggerConstants.USER_WITH_EMAIL +
                email + LoggerConstants.NOT_FOUND_IN_DB);
        }
        return new UserPrincipal(user);
    }
}
