package com.dsar.portal.repository;

import com.dsar.portal.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for User entities.
 * Uses ConcurrentHashMap for thread-safe access without external DB.
 */
@Repository
public class UserRepository {
    private final Map<String, User> store = new ConcurrentHashMap<>();

    public void save(User user) {
        store.put(user.getUsername(), user);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(store.get(username));
    }

    public boolean existsByUsername(String username) {
        return store.containsKey(username);
    }
}
