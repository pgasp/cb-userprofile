package com.cb.demo.userprofile.services.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserFoundImVO {
    private String id;
    private Long tenantId;
    private String firstName;
    private String lastName;
    private Map<String , List<String>> foundIn;
}
