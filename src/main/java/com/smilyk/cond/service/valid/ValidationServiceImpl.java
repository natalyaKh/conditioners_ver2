package com.smilyk.cond.service.valid;

import com.smilyk.cond.model.UserEntity;
import com.smilyk.cond.repo.UserEntityRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    UserEntityRepository userEntityRepository;
    @SneakyThrows
    @Override
    public void checkUniqueUser(String userEmail) {
        // TODO: 01/08/2021 write right exception
        Optional<UserEntity> userEntity = userEntityRepository.getByUserEmail(userEmail);
        if(userEntity.isPresent()) throw new Exception("TTT");
        // TODO: 01/08/2021 write right exception 
    }
}
