package com.zimra.engine.user.models;

import com.zimra.engine.user.models.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserSystem extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

    // Removed direct ManyToMany relationship with User
}
