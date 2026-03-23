package com.zimra.engine.user.dtos;

import com.zimra.engine.user.models.enums.Status;
import lombok.Data;

@Data
public class SystemDTO {
    private Long id;
    private String name;
    private String description;
    private Status status;
}