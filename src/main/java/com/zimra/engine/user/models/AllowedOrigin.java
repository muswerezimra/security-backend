package com.zimra.engine.user.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "allowed_origins")
@Getter
@Setter
public class AllowedOrigin extends BaseEntity {
    private String originUrl;

}