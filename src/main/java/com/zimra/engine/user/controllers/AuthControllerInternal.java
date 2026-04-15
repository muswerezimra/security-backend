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
@RequestMapping("/authenticate/internal")
public class AuthControllerInternal {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Token> authenticate(@RequestParam String username, @RequestParam String password) {
        Token token = new Token();
        token.setToken(authService.authenticate(username, password));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
