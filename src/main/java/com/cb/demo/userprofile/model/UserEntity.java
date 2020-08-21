package com.cb.demo.userprofile.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document
public class UserEntity {
    @Id
    private String id;
    @NotNull
    @Size(max = 2, min=2)
    private String countryCode;
    @NotNull
    private String username;
    @NotNull
    private String password;
}
