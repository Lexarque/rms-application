package org.acme.auth.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import org.acme.auth.dto.AuthResponse;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    public AuthResponse login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!BcryptUtil.matches(password, user.passwordHash)) {
            throw new RuntimeException("Password does not match");
        }

        String token = Jwt.issuer("http://rms-application-backend")
                .upn(username)
                .expiresIn(3600)
                .groups(new HashSet<>(Collections.singletonList(user.role)))
                .sign();

        return new AuthResponse(token, username, user.role);
    }
}
