package com.zimra.engine.user.repositories;

import com.zimra.engine.user.models.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemRepository extends JpaRepository<UserSystem, Long> {

    boolean existsByName(String name);

    @Query("SELECT DISTINCT r.userSystem FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<UserSystem> findAllByUserId(@Param("userId") Long userId);


    Optional<UserSystem> findUserSystemByName(String name);

}