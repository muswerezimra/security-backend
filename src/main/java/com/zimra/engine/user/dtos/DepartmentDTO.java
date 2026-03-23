package com.zimra.engine.user.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
    private LocalDateTime createdDate;

}
