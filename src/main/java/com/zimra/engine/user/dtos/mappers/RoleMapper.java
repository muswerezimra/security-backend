package com.zimra.engine.user.dtos.mappers;

import com.zimra.engine.user.dtos.RoleDTO;
import com.zimra.engine.user.models.Role;
import com.zimra.engine.user.repositories.SystemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class RoleMapper {

    private final SystemRepository systemRepository;

    public Role toEntity(String roleName, Long systemId) {
        Role role = new Role();
        role.setName(roleName);
        role.setUserSystem(systemRepository.findById(systemId).orElseThrow(() -> new RuntimeException("System not found")));
        role.setCreatedDate(LocalDateTime.now());
        return role;
    }

    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
