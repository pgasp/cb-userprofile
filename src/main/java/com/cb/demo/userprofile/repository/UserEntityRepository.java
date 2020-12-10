package com.cb.demo.userprofile.repository;

import java.util.List;

import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cb.demo.userprofile.model.UserEntity;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity,String> {

    UserEntity findByUsername(String username);


    @Query("#{#n1ql.selectEntity} where  #{#n1ql.filter}  and firstName like $1 and enabled = $2 and countryCode = $3 order by firstName desc limit $4 offset $5")
    List<UserEntity> findActiveUsersByFirstName(String firstName, boolean enabled, String countryCode,
                                                int limit, int offset);




}


