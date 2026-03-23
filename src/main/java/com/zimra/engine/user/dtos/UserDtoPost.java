package com.zimra.engine.user.dtos;

import lombok.Data;

@Data
public class UserDtoPost {
    private String fName;
    private String lName;
    private String email;
    private String username;
    private String phone;
    private Long departmentId;
}
