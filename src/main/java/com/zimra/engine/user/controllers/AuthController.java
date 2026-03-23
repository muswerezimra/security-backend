package com.zimra.engine.user.controllers;

import com.zimra.engine.user.configs.security.JwtKeyProvider;
import com.zimra.engine.user.configs.security.Token;
import com.zimra.engine.user.services.serviceImpl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Token> authenticate(@RequestParam String username, @RequestParam String password, @RequestParam Long systemId) {
        Token token = new Token();
        // String tokenValue = authService.authenticate(username, password);
        // go AD

        token.setToken(authService.authenticate(username, password, systemId));
        return new ResponseEntity<>(token, HttpStatus.OK);
        //return ResponseEntity.ok(token); // Return the JWT token
    }

    @GetMapping("/public-key")
    public ResponseEntity<Map<String, String>> getPublicKey() {
        String publicKey = Base64.getEncoder().encodeToString(JwtKeyProvider.getPublicKey().getEncoded());
        return ResponseEntity.ok(Map.of("publicKey", publicKey));
    }
}
