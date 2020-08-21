package com.cb.demo.userprofile.repository;

import com.cb.demo.userprofile.model.UserEntity;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.N1qlSecondaryIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
@N1qlPrimaryIndexed
@N1qlSecondaryIndexed(indexName = "userEntity")
public interface UserEntityRepository extends CouchbasePagingAndSortingRepository<UserEntity,String> {
}
