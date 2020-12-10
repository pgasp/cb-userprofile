package com.cb.demo.userprofile.services;


import com.cb.demo.userprofile.model.UserEntity;
import com.cb.demo.userprofile.repository.UserEntityCustomRepositoryImpl;
import com.cb.demo.userprofile.repository.UserEntityRepository;
import com.cb.demo.userprofile.services.vo.SimpleUserVO;
import com.cb.demo.userprofile.services.vo.UserFoundImVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private UserEntityCustomRepositoryImpl userEntityCustomRepository;

    @Override
    public UserEntity addUser(UserEntity user) {
        return userEntityRepository.save(user);
    }

    @Override
    public UserEntity getUser(String id) {
        Optional<UserEntity> user = userEntityRepository.findById(id);
        return user.isPresent()? user.get(): null;

    }


    @Override
    public UserEntity findByUsername(String username) {
        return userEntityRepository.findByUsername(username);
    }




    public List<SimpleUserVO> listActiveUsers(String firstName,  boolean enabled, String countryCode,  Integer limit, Integer offset ) {
        return userEntityCustomRepository.listActiveUsers(firstName, enabled, countryCode, limit, offset);

    }

    @Override
    public List<UserEntity> findActiveUsersByFirstName(String firstName, String countryCode, int limit, int offset) {
        return userEntityRepository.findActiveUsersByFirstName(firstName,true, countryCode, limit, offset);
    }


    // tag::code[]
    @Override
    public List<SimpleUserVO> ftsListActiveUsers(String firstName,  boolean enabled, String countryCode,  Integer limit, Integer offset ) {

        return userEntityCustomRepository.ftsListActiveUsers(firstName, enabled, countryCode, limit, offset);
    }

    @Override
    public List<UserFoundImVO> scoreListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer skip) {
        return userEntityCustomRepository.scoreListActiveUsers(firstName, enabled, countryCode, limit, skip);
    }


}
