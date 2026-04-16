package com.zimra.engine.user.controllers;

import com.zimra.engine.user.dtos.RoleDTO;
import com.zimra.engine.user.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/role/")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("create")
    public ResponseEntity<RoleDTO> createRole(String roleName, Long systemId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleName, systemId));
    }

    @GetMapping("get-by-system/{systemId}")
    public ResponseEntity<List<RoleDTO>> getAllRoles(@PathVariable Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRolesSystemId(roleId));
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
