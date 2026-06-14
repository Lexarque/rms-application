package org.acme.auth.dto;

public record RegisterRequest(
        String username,
        String password,
        String fullName,
        String phoneNumber
) {}