package org.acme.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import org.acme.user.dto.CreateUserRequest;
import org.acme.user.dto.UpdateUserRequest;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.UUID;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.findById;
import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.persist;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserRequest req) {
        if (userRepository.existsByUsername(req.username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.username = req.username;
        user.passwordHash = BcryptUtil.bcryptHash(req.password);
        user.fullName = req.fullName;
        user.phoneNumber = req.phoneNumber;
        user.role = req.role;

        userRepository.persist(user);
        return user;
    }

    @Transactional
    public void updateUser(UUID id, UpdateUserRequest req) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) throw new NotFoundException("Staff not found");

        existingUser.fullName = req.fullName;
        existingUser.username = req.username;
        existingUser.role = req.role;
        existingUser.phoneNumber = req.phoneNumber;
        // Only update password if provided
        if (req.password != null && !req.password.isBlank()) {
            existingUser.passwordHash = BcryptUtil.bcryptHash(req.password);
        }
        userRepository.persist(existingUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) throw new NotFoundException("Staff not found");
        userRepository.delete(existingUser);
    }
}
