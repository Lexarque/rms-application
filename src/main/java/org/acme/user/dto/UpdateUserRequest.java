package org.acme.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {
    @NotBlank(message = "Username is required")
    public String username;

    @NotBlank(message = "Password is required")
    public String password;

    @NotBlank(message = "Full name is required")
    public String fullName;

    @NotBlank(message = "Phone number is required")
    public String phoneNumber;

    @NotBlank(message = "Role is required")
    public String role;
}
