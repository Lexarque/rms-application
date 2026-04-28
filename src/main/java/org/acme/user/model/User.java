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

    @Column(nullable = false)
    public String role;

    @Column(name = "phone_number")
    public String phoneNumber;
}
