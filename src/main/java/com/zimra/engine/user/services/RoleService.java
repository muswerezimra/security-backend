package com.zimra.engine.user.services;

import com.zimra.engine.user.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(String roleName, Long systemId);

    List<RoleDTO> getRolesSystemId(Long id);

    RoleDTO getRoleById(Long id);

    void deleteRoleById(Long id);

}
