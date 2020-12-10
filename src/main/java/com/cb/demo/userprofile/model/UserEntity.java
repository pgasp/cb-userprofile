package com.cb.demo.userprofile.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.couchbase.core.index.CompositeQueryIndex;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;

import lombok.Data;

@Data
@Document
@TypeAlias("user")
@CompositeQueryIndex(fields = {"firstName","lastName asc" })
public class UserEntity {
    @Id
     private String id;
    @NotNull
    @QueryIndexed private String firstName;
    private String middleName;
    private String lastName;
    private boolean enabled;
    @NotNull
    private Integer tenantId;
    @NotNull
    @Size(max = 2, min=2)
    private String countryCode;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String socialSecurityNumber;

    private List<TelephoneEntity> telephones;
    private List<PreferenceEntity> preferences;
    private List<AddressEntity> addresses;
    private List<String> securityRoles;
}
