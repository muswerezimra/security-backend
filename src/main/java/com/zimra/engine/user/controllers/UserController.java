package com.zimra.engine.user.controllers;

import com.zimra.engine.user.dtos.UserDTO;
import com.zimra.engine.user.dtos.UserDtoPost;
import com.zimra.engine.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user details")
    public ResponseEntity<UserDTO> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one user by id")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDtoPost userDtoPost) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDtoPost));
    }

    @PostMapping("/assign-role")
    @Operation(summary = "Assign role to user")
    public ResponseEntity<UserDTO> assignRole(@RequestParam Long id, @RequestParam Long roleId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.assignRole(id, roleId));
    }

    @PostMapping("/unassign-role")
    @Operation(summary = "remove role from user")
    public ResponseEntity<UserDTO> unassignRole(@RequestParam Long id, @RequestParam Long roleId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.unassignRole(id, roleId));
    }

    @PostMapping("/assign-multiple-roles")
    @Operation(summary = "Assign multiple roles to user")
    public ResponseEntity<UserDTO> assignMultipleRole(@RequestParam Long id, @RequestParam List<Long> roleIds) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.assignManyRoles(id, roleIds));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('TaRMS_Dev')")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getAll());
    }

    @GetMapping("/get-by-system/{systemId}")
    @Operation(summary = "Get all users of a particular system")
    public ResponseEntity<List<UserDTO>> getBySystemId(@PathVariable Long systemId) {
        return ResponseEntity.ok(userService.findAllBySystemId(systemId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDtoPost userDtoPost) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(id, userDtoPost));
    }

    @PostMapping("/change-user-status/{id}")
    public ResponseEntity<UserDTO> changeUserStatus(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.changeStatus(id));
    }
}
