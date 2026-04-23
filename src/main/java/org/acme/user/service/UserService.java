package org.acme.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.user.dto.CreateUserRequest;
import org.acme.user.model.User;
import org.acme.user.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

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
        user.role = "staff";

        userRepository.persist(user);
        return user;
    }
}
