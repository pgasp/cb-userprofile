package com.cb.demo.userprofile.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TelephoneEntity {

    @NotNull
    private String name;
    @NotNull
    private String number;
}