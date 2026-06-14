package org.acme.auth.service;

import java.util.Collections;
import java.util.HashSet;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.acme.auth.dto.AuthResponse;
import org.acme.auth.dto.RegisterRequest;

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
            throw new BadRequestException("Invalid username or password");
        }

        if (!BcryptUtil.matches(password, user.passwordHash)) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = buildToken(username, user.role);

        return new AuthResponse(token, user.id, username, user.role);
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new BadRequestException("Username is required");
        }
        if (request.password() == null || request.password().length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters");
        }
        if (request.fullName() == null || request.fullName().isBlank()) {
            throw new BadRequestException("Full name is required");
        }

        if (userRepository.existsByUsername(request.username().trim())) {
            throw new BadRequestException("Username already taken");
        }

        User user = new User();
        user.username = request.username().trim();
        user.passwordHash = BcryptUtil.bcryptHash(request.password());
        user.fullName = request.fullName().trim();
        user.phoneNumber = request.phoneNumber() != null ? request.phoneNumber().trim() : null;
        user.role = "customer";

        userRepository.persist(user);

        String token = buildToken(user.username, user.role);

        return new AuthResponse(token, user.id, user.username, user.role);
    }

    private String buildToken(String username, String role) {
        return Jwt.issuer("http://rms-application-backend")
                .upn(username)
                .expiresIn(3600)
                .groups(new HashSet<>(Collections.singletonList(role)))
                .sign();
    }
}