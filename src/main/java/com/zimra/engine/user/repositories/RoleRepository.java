package com.zimra.engine.user.repositories;

import com.zimra.engine.user.models.Role;
import com.zimra.engine.user.models.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r JOIN r.users u WHERE r.userSystem.id = :systemId AND u.id = :userId")
    Set<Role> findRolesBySystemIdAndUserId(@Param("systemId") Long systemId, @Param("userId") Long userId);

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u = :userSystem")
    List<Role> findRolesByUserSystem(@Param("userSystem") UserSystem userSystem);

}