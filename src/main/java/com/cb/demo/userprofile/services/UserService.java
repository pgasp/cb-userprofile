package com.cb.demo.userprofile.services;


import com.cb.demo.userprofile.model.UserEntity;
import com.cb.demo.userprofile.services.vo.SimpleUserVO;
import com.cb.demo.userprofile.services.vo.UserFoundImVO;

import java.util.List;

public interface UserService {

    UserEntity addUser(UserEntity user);
    UserEntity getUser(String id);
    UserEntity findByUsername(String username);



    List<SimpleUserVO> listActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer offset );


    List<UserEntity> findActiveUsersByFirstName(String firstName, String countryCode,int limit, int offset);

    List<SimpleUserVO> ftsListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer skip );
    public List<UserFoundImVO> scoreListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer skip );
}
