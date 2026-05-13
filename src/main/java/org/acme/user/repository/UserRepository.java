package org.acme.user.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.acme.user.model.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public boolean existsByUsername(String username) {
        return count("username", username) > 0;
    }

    public User findById(UUID id) {
        return find("id", id).firstResult();
    }

    public PanacheQuery<User> findStaff(String name) {
        String searchTerm = (name != null && !name.trim().isEmpty()) ? "%" + name + "%" : "%%";
        return find("fullName LIKE :name and role = :role",
                Map.of(
                        "name", searchTerm,
                        "role", "staff"
                ));
    }

    public long countAllStaff() {
        return count("role", "staff");
    }


}
