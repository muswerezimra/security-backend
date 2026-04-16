package com.zimra.engine.user.services.serviceImpl;

import com.zimra.engine.user.configs.security.JwtUtil;
import com.zimra.engine.user.models.Role;
import com.zimra.engine.user.models.User;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.repositories.SystemRepository;
import com.zimra.engine.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final SystemRepository systemRepository;
    private final JwtUtil jwtTokenUtil;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    /**
     * Authenticates a user against a specific system ID.
     */
    public String authenticate(String username, String password, Long systemId) {
        UserSystem system = systemRepository.findById(systemId)
                .orElseThrow(() -> new RuntimeException("System with ID " + systemId + " not found"));
        
        return processAuthentication(username, password, system);
    }

    /**
     * Authenticates a user against the default 'UserConnect' system.
     */
    public String authenticate(String username, String password) {
        UserSystem system = systemRepository.findUserSystemByName("UserConnect")
                .orElseThrow(() -> new RuntimeException("Default system 'UserConnect' not found"));
        
        return processAuthentication(username, password, system);
    }

    /**
     * Core authentication logic shared across entry points.
     */
    private String processAuthentication(String username, String password, UserSystem system) {
        // 1. External Active Directory Validation
        if (!verifyADPassword(username, password)) {
            throw new RuntimeException("Invalid credentials");
        }

        // 2. Fetch User and check status
        User user = userRepository.findByUsername(username);
        if (user == null || !user.isActive()) {
            throw new RuntimeException("User account not found or inactive in the internal system");
        }

        // 3. Filter roles for the target system
        Set<String> rolesForSystem = user.getRoles().stream()
                .filter(role -> role.getUserSystem().getId().equals(system.getId()))
                .map(Role::getName)
                .collect(Collectors.toSet());

        if (rolesForSystem.isEmpty()) {
            throw new RuntimeException("Access Denied: User '" + username + "' has no roles assigned for system '" + system.getName() + "'");
        }

        // 4. Generate Token
        return jwtTokenUtil.generateToken(user.getUsername(), rolesForSystem, system);
    }

    /**
     * Verifies credentials against the external Active Directory API.
     */
    private boolean verifyADPassword(String username, String password) {
        try {
            String requestBody = String.format("{\"Username\":\"%s\",\"Password\":\"%s\"}", username, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://10.16.83.32:8087/api/auth"))
                    .timeout(Duration.ofSeconds(8))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return Boolean.parseBoolean(response.body());

        } catch (Exception e) {
            log.error("Active Directory authentication failed for user {}: {}", username, e.getMessage());
            throw new RuntimeException("External authentication service is currently unavailable");
        }
    }
}
