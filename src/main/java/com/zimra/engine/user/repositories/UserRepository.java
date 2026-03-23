package com.zimra.engine.user.repositories;

import com.zimra.engine.user.models.User;
import com.zimra.engine.user.models.UserSystem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles", "roles.userSystem"})
    User findByUsername(String username);
    
}