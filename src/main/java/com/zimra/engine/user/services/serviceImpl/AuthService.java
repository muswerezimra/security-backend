package com.zimra.engine.user.services.serviceImpl;

import com.zimra.engine.user.configs.security.JwtUtil;
import com.zimra.engine.user.models.Role;
import com.zimra.engine.user.models.User;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.repositories.RoleRepository;
import com.zimra.engine.user.repositories.SystemRepository;
import com.zimra.engine.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private JwtUtil jwtTokenUtil;


    public String authenticate(String username, String password, Long systemId) {

        // Step 1: External API validation
        boolean isValid = verifyADPassword(username, password);

        if (!isValid) {
            throw new RuntimeException("Invalid credentials");
        }

        // Step 2: Internal checks
        User user = userRepository.findByUsername(username);

        if (user == null || !user.isActive()) {
            throw new RuntimeException("User not found or inactive");
        }

        UserSystem userSystem = systemRepository.findById(systemId).orElseThrow(() -> new RuntimeException("System not found"));
        UserSystem system = systemRepository.findById(systemId)
                .orElseThrow(() -> new RuntimeException("System not found"));
        Set<Role> rolesForSystem = user.getRoles().stream()
                .filter(role -> role.getUserSystem().getId().equals(systemId))
                .collect(Collectors.toSet());

        if (rolesForSystem.isEmpty()) {
            throw new RuntimeException("User has no access to this system");
        }

//        UserSystem system = systemRepository.findById(systemId)
//                .orElseThrow(() -> new RuntimeException("System not found"));

        Set<String> roleNames = rolesForSystem.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return jwtTokenUtil.generateToken(user.getUsername(), roleNames, system);
    }

    public boolean verifyADPassword(String username, String password) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            String requestBody = String.format(
                    "{\"Username\":\"%s\",\"Password\":\"%s\"}",
                    username, password
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://10.16.83.32:8087/api/auth"))
                    .timeout(Duration.ofSeconds(8))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            // Assuming response body is: true or false
            return Boolean.parseBoolean(response.body());

        } catch (Exception e) {
            throw new RuntimeException("External authentication service failed", e);
        }
    }

}