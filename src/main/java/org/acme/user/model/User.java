package org.acme.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.acme.shared.model.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    public String username;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    public String passwordHash;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    @Column(name = "role", nullable = false)
    public String role;

    @Column(name = "phone_number")
    public String phoneNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
