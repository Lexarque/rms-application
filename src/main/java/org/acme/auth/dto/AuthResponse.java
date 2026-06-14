package org.acme.auth.dto;

import java.util.UUID;

public record AuthResponse(
        String token,
        UUID id,
        String username,
        String role
) {}