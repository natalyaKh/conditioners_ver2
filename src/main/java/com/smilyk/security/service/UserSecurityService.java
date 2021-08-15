package com.smilyk.security.service;

import com.smilyk.constants.LoggerConstants;
import com.smilyk.model.UserEntity;
import com.smilyk.repo.UserEntityRepository;
import com.smilyk.security.modelAndDto.UserPrincipal;
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
