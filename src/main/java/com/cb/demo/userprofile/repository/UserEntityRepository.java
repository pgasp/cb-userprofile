package com.cb.demo.userprofile.repository;

import com.cb.demo.userprofile.model.UserEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.N1qlSecondaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

@N1qlPrimaryIndexed
@N1qlSecondaryIndexed(indexName = "userEntity")
public interface UserEntityRepository extends CouchbasePagingAndSortingRepository<UserEntity,String> {

    UserEntity findByUsername(String username);


    @Query("#{#n1ql.selectEntity} where  #{#n1ql.filter}  and firstName like $1 and enabled = $2 and countryCode = $3 order by firstName desc limit $4 offset $5")
    List<UserEntity> findActiveUsersByFirstName(String firstName, boolean enabled, String countryCode,
                                                int limit, int offset);




}


