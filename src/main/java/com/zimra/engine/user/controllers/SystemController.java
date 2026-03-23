package com.zimra.engine.user.controllers;

import com.zimra.engine.user.dtos.SystemDTO;
import com.zimra.engine.user.dtos.SystemDtoPost;
import com.zimra.engine.user.services.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j

@RequestMapping("/api/v1/systems")
public class SystemController {
    private final SystemService systemService;


    @GetMapping("/{id}")
    //@Operation(summary = "Get  one application by id")
    public ResponseEntity<SystemDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(systemService.getById(id));
    }

    @PostMapping("create")
//    @PreAuthorize("hasAuthority('UserConnect_Admin')")
    @Operation(summary = "Create a new system (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "System created"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Admin role required")
    })
    public ResponseEntity<SystemDTO> createSystem(@RequestBody SystemDtoPost systemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(systemService.createSystem(systemDTO));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all systems")
    public ResponseEntity<List<SystemDTO>> getAllSystems() {
        return ResponseEntity.status(HttpStatus.OK).body(systemService.getAllSystems());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<SystemDTO>> getUserSystems(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(systemService.getByUserId(id));
    }

}
