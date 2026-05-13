package org.acme.user.dto;

import org.acme.user.model.User;

import java.util.UUID;

public record StaffDto(
        UUID id,
        String fullName,
        String username,
        String role,
        String phoneNumber
) {
    public static StaffDto from(User user) {
        return new StaffDto(
                user.id,
                user.fullName,
                user.username,
                user.role,
                user.phoneNumber
        );
    }
}