package com.zimra.engine.user.configs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.zimra.engine.user.models.User;
import com.zimra.engine.user.models.UserSystem;
import com.zimra.engine.user.repositories.SystemRepository;
import com.zimra.engine.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SystemRepository systemRepository;

    public String generateToken(String username, Set<String> roles, UserSystem system) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, roles, system);
    }

    public String createToken(Map<String, Object> claims, String subject, Set<String> roles, UserSystem system) {
        // Use RSA256 with Private Key for signing
        RSAPublicKey publicKey = JwtKeyProvider.getPublicKey();
        RSAPrivateKey privateKey = JwtKeyProvider.getPrivateKey();
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10); // 10 hours

        // Build the token
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .withClaim("claims", claims) // Add any additional claims as needed
                .withClaim("systemId", system.getId().toString())
                .withClaim("systemName", system.getName())
                .withArrayClaim("roles", roles.toArray(new String[0]))
                .sign(algorithm);
    }

    // Method to extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Method to extract all claims from token
    DecodedJWT extractAllClaims(String token) {
        // Use RSA256 with Public Key for verification
        RSAPublicKey publicKey = JwtKeyProvider.getPublicKey();
        // Private key is not needed for verification
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    // Method to check if the token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiresAt();
        return expiration.before(new Date());
    }

    // Method to validate the token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }


    public boolean isUserMappedToSystem(String username, Long systemId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        //List<UserSystem> userSystems = systemRepository.findAllByUserId(user.getId());
        Set<UserSystem> userSystems = user.getUserSystems();


        return userSystems.stream()
                .anyMatch(system -> system.getId().equals(systemId));
    }
}