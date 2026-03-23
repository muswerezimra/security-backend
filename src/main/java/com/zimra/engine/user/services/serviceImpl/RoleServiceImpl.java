package com.zimra.engine.user.services.serviceImpl;

import com.zimra.engine.user.dtos.RoleDTO;
import com.zimra.engine.user.dtos.mappers.RoleMapper;
import com.zimra.engine.user.models.Role;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.repositories.RoleRepository;
import com.zimra.engine.user.repositories.SystemRepository;
import com.zimra.engine.user.services.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final ModelMapper modelMapper;
    private RoleRepository roleRepository;
    private SystemRepository systemRepository;
    private RoleMapper roleMapper;

    @Override
    public RoleDTO createRole(String roleName, Long systemId) {
        Role role = roleMapper.toEntity(roleName, systemId);
        return modelMapper.map(roleRepository.save(role), RoleDTO.class);
    }

    @Override
    public List<RoleDTO> getRolesSystemId(Long id) {
        UserSystem userSystem = systemRepository.findById(id).orElseThrow(() -> new RuntimeException("System not found"));
        List<Role> roles = roleRepository.findRolesByUserSystem(userSystem);
        return roles.stream().map(roleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return roleMapper.toDTO(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        roleRepository.delete(role);
    }
}
