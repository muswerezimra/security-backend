package com.zimra.engine.user.repositories;


import com.zimra.engine.user.models.AllowedOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowedOriginRepository extends JpaRepository<AllowedOrigin, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
}