package com.cb.demo.userprofile.repository;

import com.cb.demo.userprofile.services.vo.SimpleUserVO;
import com.cb.demo.userprofile.services.vo.UserFoundImVO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserEntityCustomRepository {

    public List<SimpleUserVO> ftsListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer offset ) ;
    public List<SimpleUserVO> listActiveUsers(String firstName,  boolean enabled, String countryCode,  Integer limit, Integer offset );
    public List<UserFoundImVO> scoreListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer skip );
}
