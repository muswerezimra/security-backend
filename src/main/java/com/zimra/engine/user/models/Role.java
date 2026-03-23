package com.zimra.engine.user.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "system_id", nullable = false)
    private UserSystem userSystem;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
