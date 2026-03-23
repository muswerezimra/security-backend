package com.zimra.engine.user.dtos;

import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private System system; // Reference to the System ID
}