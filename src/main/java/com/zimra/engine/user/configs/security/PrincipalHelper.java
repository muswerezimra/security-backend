package com.zimra.engine.user.configs.security;

import com.zimra.engine.user.models.User;
import com.zimra.engine.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalHelper {

    private final UserRepository userRepository;

    public User getPrincipal() {
        String currentLoggedUserName = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.findByUsername(currentLoggedUserName);
    }

}