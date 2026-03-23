package com.zimra.engine.user.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Department extends BaseEntity {
    private String name;
    private String description;
    private String code;
}
